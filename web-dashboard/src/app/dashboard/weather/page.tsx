'use client';

import { useState, useEffect } from 'react';
import { supabase } from '@/lib/supabase';
import {
  CloudSun, Droplets, Wind, Thermometer, CloudRain, Sun, Cloud,
  AlertTriangle, Sprout,
} from 'lucide-react';

interface DailyWeather {
  date: string;
  temp_max: number;
  temp_min: number;
  temp_avg: number;
  humidity_avg: number;
  total_precipitation_mm: number;
  wind_avg_kph: number;
  condition: string;
  condition_desc: string;
}

interface WeatherResponse {
  city: string;
  daily: DailyWeather[];
  farming_advisories: string[];
}

export default function WeatherPage() {
  const [weather, setWeather] = useState<WeatherResponse | null>(null);
  const [loading, setLoading] = useState(true);
  const [lat, setLat] = useState(28.7136); // Default: Delhi
  const [lon, setLon] = useState(77.1747);

  useEffect(() => {
    // Try to get user's location
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition(
        (pos) => {
          setLat(pos.coords.latitude);
          setLon(pos.coords.longitude);
        },
        () => {
          // Use default (Delhi)
          loadWeather(lat, lon);
        }
      );
    }
    loadWeather(lat, lon);
  }, []);

  useEffect(() => {
    loadWeather(lat, lon);
  }, [lat, lon]);

  async function loadWeather(latitude: number, longitude: number) {
    setLoading(true);
    try {
      const supabaseUrl = process.env.NEXT_PUBLIC_SUPABASE_URL;
      const res = await fetch(
        `${supabaseUrl}/functions/v1/weather-proxy?lat=${latitude}&lon=${longitude}`,
        { method: 'GET' }
      );
      if (res.ok) {
        const data = await res.json();
        setWeather(data);
      }
    } catch (err) {
      console.error('Weather load error:', err);
    } finally {
      setLoading(false);
    }
  }

  function getWeatherIcon(condition: string) {
    switch (condition?.toLowerCase()) {
      case 'rain':
      case 'drizzle': return <CloudRain size={32} color="#1565C0" />;
      case 'clear': return <Sun size={32} color="#FFA000" />;
      case 'clouds': return <Cloud size={32} color="#757575" />;
      default: return <CloudSun size={32} color="#689F38" />;
    }
  }

  function getWeatherBg(condition: string) {
    switch (condition?.toLowerCase()) {
      case 'rain': return 'linear-gradient(135deg, #E3F2FD 0%, #BBDEFB 100%)';
      case 'clear': return 'linear-gradient(135deg, #FFF8E1 0%, #FFECB3 100%)';
      case 'clouds': return 'linear-gradient(135deg, #F5F5F5 0%, #E0E0E0 100%)';
      default: return 'linear-gradient(135deg, #E8F5E9 0%, #C8E6C9 100%)';
    }
  }

  if (loading) {
    return (
      <div>
        <div className="card" style={{ height: 200, marginBottom: 24 }}>
          <div className="skeleton" style={{ width: '100%', height: '100%' }} />
        </div>
        <div className="grid-3">
          {[1, 2, 3, 4, 5].map(i => (
            <div key={i} className="card" style={{ height: 160 }}>
              <div className="skeleton" style={{ width: '100%', height: '100%' }} />
            </div>
          ))}
        </div>
      </div>
    );
  }

  const today = weather?.daily?.[0];

  return (
    <div>
      {/* Today's Weather Hero */}
      {today && (
        <div className="card" style={{ marginBottom: 24, background: getWeatherBg(today.condition), border: 'none' }}>
          <div className="card-body" style={{ padding: '28px 32px' }}>
            <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'space-between', flexWrap: 'wrap', gap: 20 }}>
              <div style={{ display: 'flex', alignItems: 'center', gap: 20 }}>
                {getWeatherIcon(today.condition)}
                <div>
                  <h2 style={{ fontSize: '1.5rem', fontWeight: 700, marginBottom: 2 }}>
                    {weather?.city || 'Your Location'}
                  </h2>
                  <p style={{ color: 'var(--color-text-secondary)', fontSize: '0.85rem', textTransform: 'capitalize' }}>
                    {today.condition_desc}
                  </p>
                </div>
              </div>
              <div style={{ textAlign: 'right' }}>
                <div style={{ fontSize: '3rem', fontWeight: 700, lineHeight: 1 }}>
                  {Math.round(today.temp_avg)}°C
                </div>
                <p style={{ fontSize: '0.8rem', color: 'var(--color-text-secondary)' }}>
                  H: {Math.round(today.temp_max)}° · L: {Math.round(today.temp_min)}°
                </p>
              </div>
            </div>

            {/* Today's Details */}
            <div style={{ display: 'flex', gap: 24, marginTop: 20, flexWrap: 'wrap' }}>
              <div style={{ display: 'flex', alignItems: 'center', gap: 8 }}>
                <Droplets size={16} color="var(--color-info)" />
                <span style={{ fontSize: '0.85rem' }}>
                  <strong>{today.humidity_avg}%</strong> Humidity
                </span>
              </div>
              <div style={{ display: 'flex', alignItems: 'center', gap: 8 }}>
                <CloudRain size={16} color="var(--color-info)" />
                <span style={{ fontSize: '0.85rem' }}>
                  <strong>{today.total_precipitation_mm}mm</strong> Rain
                </span>
              </div>
              <div style={{ display: 'flex', alignItems: 'center', gap: 8 }}>
                <Wind size={16} color="var(--color-text-secondary)" />
                <span style={{ fontSize: '0.85rem' }}>
                  <strong>{today.wind_avg_kph} km/h</strong> Wind
                </span>
              </div>
            </div>
          </div>
        </div>
      )}

      {/* 5-Day Forecast */}
      <div className="section-header">
        <h2>📅 5-Day Forecast</h2>
      </div>
      <div className="grid-3" style={{ marginBottom: 24 }}>
        {weather?.daily?.map((day, i) => (
          <div key={day.date} className="card animate-in" style={{ animationDelay: `${i * 0.06}s` }}>
            <div className="card-body" style={{ padding: 20, textAlign: 'center' }}>
              <div style={{ fontSize: '0.8rem', fontWeight: 600, color: 'var(--color-text-secondary)', marginBottom: 8 }}>
                {i === 0 ? 'Today' : new Date(day.date).toLocaleDateString('en-IN', { weekday: 'short', day: 'numeric', month: 'short' })}
              </div>
              <div style={{ margin: '8px 0' }}>
                {getWeatherIcon(day.condition)}
              </div>
              <div style={{ fontSize: '0.75rem', color: 'var(--color-text-secondary)', textTransform: 'capitalize', marginBottom: 8 }}>
                {day.condition_desc}
              </div>
              <div style={{ fontSize: '1.5rem', fontWeight: 700 }}>
                {Math.round(day.temp_avg)}°C
              </div>
              <div style={{ fontSize: '0.75rem', color: 'var(--color-text-secondary)' }}>
                {Math.round(day.temp_max)}° / {Math.round(day.temp_min)}°
              </div>

              <div style={{
                marginTop: 12, paddingTop: 12,
                borderTop: '1px solid var(--color-border-light)',
                display: 'flex', justifyContent: 'space-around', fontSize: '0.7rem',
              }}>
                <div>
                  <Droplets size={12} color="var(--color-info)" />
                  <div>{day.humidity_avg}%</div>
                </div>
                <div>
                  <CloudRain size={12} color="var(--color-info)" />
                  <div>{day.total_precipitation_mm}mm</div>
                </div>
                <div>
                  <Wind size={12} color="var(--color-text-secondary)" />
                  <div>{day.wind_avg_kph}km/h</div>
                </div>
              </div>
            </div>
          </div>
        ))}
      </div>

      {/* Farming Advisories */}
      {weather?.farming_advisories && weather.farming_advisories.length > 0 && (
        <div className="card card-accent-warning">
          <div className="card-header">
            <h3>🌾 Weather-Based Farming Advice</h3>
          </div>
          <div className="card-body">
            <div style={{ display: 'flex', flexDirection: 'column', gap: 10 }}>
              {weather.farming_advisories.map((adv, i) => (
                <div key={i} style={{
                  padding: '12px 16px',
                  background: 'var(--color-bg-secondary)',
                  borderRadius: 'var(--radius-md)',
                  display: 'flex', alignItems: 'flex-start', gap: 10,
                  fontSize: '0.85rem', lineHeight: 1.5,
                }}>
                  <Sprout size={16} style={{ flexShrink: 0, marginTop: 3, color: 'var(--color-secondary)' }} />
                  {adv}
                </div>
              ))}
            </div>
          </div>
        </div>
      )}
    </div>
  );
}
