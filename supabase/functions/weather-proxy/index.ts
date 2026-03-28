import "jsr:@supabase/functions-js/edge-runtime.d.ts";
import { createClient } from "https://esm.sh/@supabase/supabase-js@2";

const corsHeaders = {
  'Access-Control-Allow-Origin': '*',
  'Access-Control-Allow-Headers': 'authorization, x-client-info, apikey, content-type',
  'Access-Control-Allow-Methods': 'POST, GET, OPTIONS',
};

Deno.serve(async (req: Request) => {
  if (req.method === 'OPTIONS') {
    return new Response('ok', { headers: corsHeaders });
  }

  try {
    const url = new URL(req.url);
    let lat: string | null = null;
    let lon: string | null = null;

    if (req.method === 'GET') {
      lat = url.searchParams.get('lat');
      lon = url.searchParams.get('lon');
    } else {
      const body = await req.json();
      lat = body.lat?.toString();
      lon = body.lon?.toString();
    }

    if (!lat || !lon) {
      return new Response(
        JSON.stringify({ error: 'lat and lon are required' }),
        { status: 400, headers: { ...corsHeaders, 'Content-Type': 'application/json' } }
      );
    }

    // ──── Open-Meteo API (free, no key needed) ────
    const meteoUrl = `https://api.open-meteo.com/v1/forecast?latitude=${lat}&longitude=${lon}&daily=temperature_2m_max,temperature_2m_min,precipitation_sum,windspeed_10m_max,relative_humidity_2m_mean,weathercode&hourly=temperature_2m,relative_humidity_2m,precipitation,windspeed_10m,weathercode&timezone=auto&forecast_days=7`;
    const meteoRes = await fetch(meteoUrl);
    const meteoData = await meteoRes.json();

    if (!meteoRes.ok) {
      throw new Error(meteoData.reason || 'Open-Meteo API error');
    }

    // ──── Reverse geocode city name ────
    let city = `${parseFloat(lat).toFixed(2)}°N, ${parseFloat(lon).toFixed(2)}°E`;
    try {
      // Switched from Open-Meteo's non-existent /reverse to BigDataCloud Free Reverse Geocoder
      const geoUrl = `https://api.bigdatacloud.net/data/reverse-geocode-client?latitude=${lat}&longitude=${lon}&localityLanguage=en`;
      const geoRes = await fetch(geoUrl);
      if (geoRes.ok) {
        const geoData = await geoRes.json();
        // Fallback elegantly through the payload's precision
        const foundCity = geoData.city || geoData.locality || geoData.principalSubdivision;
        if (foundCity) {
          city = foundCity;
        }
      }
    } catch (_) { /* ignore geocode failure */ }

    // ──── WMO weather code → description ────
    function wmoToCondition(code: number): { condition: string; desc: string; icon: string } {
      if (code === 0) return { condition: 'Clear', desc: 'Clear sky', icon: '01d' };
      if (code <= 3) return { condition: 'Clouds', desc: ['Mainly clear', 'Partly cloudy', 'Overcast'][code - 1], icon: '02d' };
      if (code <= 49) return { condition: 'Fog', desc: 'Fog', icon: '50d' };
      if (code <= 59) return { condition: 'Drizzle', desc: 'Drizzle', icon: '09d' };
      if (code <= 69) return { condition: 'Rain', desc: code <= 63 ? 'Light rain' : 'Heavy rain', icon: '10d' };
      if (code <= 79) return { condition: 'Snow', desc: 'Snow', icon: '13d' };
      if (code <= 84) return { condition: 'Rain', desc: 'Rain showers', icon: '09d' };
      if (code <= 89) return { condition: 'Snow', desc: 'Snow showers', icon: '13d' };
      if (code <= 99) return { condition: 'Thunderstorm', desc: 'Thunderstorm', icon: '11d' };
      return { condition: 'Unknown', desc: 'Unknown', icon: '01d' };
    }

    // ──── Build hourly data (next 24h) ────
    const hourly: any[] = [];
    const hData = meteoData.hourly;
    const nowHour = new Date().getHours();
    
    // Capture the exact real-time temperature right now!
    let currentTemp = hData.temperature_2m[nowHour];

    for (let i = nowHour; i < Math.min(nowHour + 24, hData.time.length); i++) {
      const wmo = wmoToCondition(hData.weathercode[i]);
      hourly.push({
        datetime: hData.time[i].replace('T', ' '),
        temp_celsius: hData.temperature_2m[i],
        humidity: hData.relative_humidity_2m[i],
        precipitation_mm: hData.precipitation[i] || 0,
        wind_speed_kph: hData.windspeed_10m[i],
        condition: wmo.condition,
        condition_desc: wmo.desc,
        icon: wmo.icon,
      });
    }

    // ──── Build daily data ────
    const dData = meteoData.daily;
    const daily: any[] = [];
    for (let i = 0; i < dData.time.length; i++) {
      const wmo = wmoToCondition(dData.weathercode[i]);
      const tMax = dData.temperature_2m_max[i];
      const tMin = dData.temperature_2m_min[i];
      
      // The default behavior was averaging Max and Min (e.g., 40 + 20 / 2 = 30°C) which looks grossly inaccurate during peaks.
      let displayTemp = Math.round((tMax + tMin) / 2 * 10) / 10;
      
      // OVERRIDE: If this is "Today" (index 0), forcefully overwrite the daily "avg" to be the REAL-TIME Temperature!
      // This allows both the Web and Android apps to display actual Current Weather instantly without recompiling native code.
      if (i === 0 && currentTemp !== undefined) {
        displayTemp = currentTemp;
      }

      daily.push({
        date: dData.time[i],
        temp_max: tMax,
        temp_min: tMin,
        temp_avg: displayTemp,
        humidity_avg: dData.relative_humidity_2m_mean?.[i] ?? 60,
        total_precipitation_mm: Math.round((dData.precipitation_sum[i] || 0) * 10) / 10,
        wind_avg_kph: Math.round((dData.windspeed_10m_max[i] || 0) * 10) / 10,
        condition: wmo.condition,
        condition_desc: wmo.desc,
        icon: wmo.icon,
      });
    }

    // ──── Farming advisories ────
    const advisories = generateWeatherAdvisory(daily);

    // ──── Store in DB for offline ────
    try {
      const supabaseUrl = Deno.env.get('SUPABASE_URL')!;
      const supabaseKey = Deno.env.get('SUPABASE_SERVICE_ROLE_KEY')!;
      const supabase = createClient(supabaseUrl, supabaseKey);

      for (const d of daily) {
        await supabase.from('e_weather_data').upsert({
          location_lat: parseFloat(lat),
          location_lon: parseFloat(lon),
          recorded_at: d.date + 'T12:00:00Z',
          temp_celsius_max: d.temp_max,
          temp_celsius_min: d.temp_min,
          humidity_percent: d.humidity_avg,
          precipitation_mm: d.total_precipitation_mm,
          wind_speed_kph: d.wind_avg_kph,
          condition_desc: d.condition_desc,
        });
      }
    } catch (dbErr) {
      console.error('DB store failed (non-fatal):', dbErr);
    }

    return new Response(
      JSON.stringify({
        location: { lat: parseFloat(lat), lon: parseFloat(lon) },
        city,
        hourly,
        daily,
        farming_advisories: advisories,
      }),
      { headers: { ...corsHeaders, 'Content-Type': 'application/json' } }
    );
  } catch (error) {
    return new Response(
      JSON.stringify({ error: error.message }),
      { status: 500, headers: { ...corsHeaders, 'Content-Type': 'application/json' } }
    );
  }
});

function generateWeatherAdvisory(daily: any[]): string[] {
  const advisories: string[] = [];
  const today = daily[0];
  
  if (today) {
    if (today.total_precipitation_mm > 10) {
      advisories.push('Heavy rainfall expected. Delay any spraying operations. Ensure proper drainage in fields.');
    } else if (today.total_precipitation_mm > 2) {
      advisories.push('Light to moderate rain expected. Good time for transplanting if soil moisture was low.');
    }
    
    if (today.temp_max > 40) {
      advisories.push('Extreme heat warning. Irrigate crops during early morning or late evening. Avoid mid-day field work.');
    } else if (today.temp_max > 35) {
      advisories.push('High temperatures expected. Ensure adequate irrigation for standing crops.');
    }
    
    if (today.wind_avg_kph > 30) {
      advisories.push('Strong winds expected. Secure any temporary structures. Avoid applying pesticide sprays.');
    }
    
    if (today.humidity_avg > 85) {
      advisories.push('High humidity conditions. Monitor crops for fungal diseases. Consider preventive fungicide if needed.');
    }

    // Multi-day trends
    const totalRainWeek = daily.reduce((sum: number, d: any) => sum + d.total_precipitation_mm, 0);
    if (totalRainWeek > 50) {
      advisories.push('Significant rainfall expected this week. Plan harvesting accordingly and arrange for crop drying.');
    } else if (totalRainWeek < 2 && daily.length >= 5) {
      advisories.push('Dry spell expected this week. Plan irrigation schedules and conserve water reserves.');
    }
  }
  
  if (advisories.length === 0) {
    advisories.push('Weather conditions are favorable for normal farming activities.');
  }
  
  return advisories;
}
