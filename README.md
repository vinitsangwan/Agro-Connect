# 🌱 Agro-Connect (V1)

**Agro-Connect** is a comprehensive, production-ready Agritech platform engineered to bridge the digital divide for rural farmers. Featuring an **Offline-First Android App** and a glassmorphic **Next.js Web Dashboard**, the ecosystem empowers agricultural communities with localized, ML-driven price forecasting, marketplace access to buyers, integrated OSMMaps, and highly scalable offline caching.

---

## 🚀 Key Features

### 🛒 1. Peer-to-Peer Marketplace
Farmers and buyers can connect directly using the integrated marketplace. Contains dynamic escrow status monitoring, a robust shopping cart, and offline-compatible payment confirmation flows.

### 📈 2. AI 7-Day Price Predictions (PFE)
Supabase Edge Functions process daily crop variables, running an internal ML pipeline to project 7-day crop price forecasts (`p_prediction_outputs`) directly onto interactive `recharts` overlays and mobile grids. Includes "confidence scores" and calculated ideal "sell windows".

### 📶 3. Absolute Offline-First Experience (Room DB)
Rural 2G endpoints shouldn't handicap farmers. The Android Mobile architecture heavily caches Marketplace listings, Mandi coordinates, Advisories, and 7-day weather predictions into a local **SQLite Room Database**.
- `SyncWorker` powered by **WorkManager** quietly synchronizes background data states to Supabase every 12 hours.

### 🗺️ 4. Local Mandi GPS Mapping
Users can visually browse surrounding crop markets. Uses **OSMDroid** native mapping components embedded directly into Android, calculating Euclidean and navigational paths without relying on Google Maps API keys.

### 🌍 5. Deep Multi-Lingual Integration (i18n)
Both Web and Mobile platforms natively support English (`en`), Hindi (`hi`), and Marathi (`mr`). Powered by `react-i18next` for seamless client-side hydration without Next.js mismatch errors, and localized Android String resources.

---

## 🛠️ Technology Stack

### 📱 Full-Stack Architecture
- **Web Frontend**: React, Next.js (App Router), Lucide Icons, `react-i18next`, Recharts.
- **Mobile Client**: Kotlin, Jetpack Compose, Navigation-Compose, Room (SQLite), Ktor HTTP, Kotlinx-Serialization.
- **Backend Infrastructure**: Supabase (PostgreSQL), GoTrue Auth, Edge Functions (Deno).

### 🛡️ Security & Accessibility Compliance (WCAG AA)
Agro-Connect adheres to top-tier enterprise compliance metrics.
- **A11y Validated**: Dynamic regex-inserted `contentDescription` properties across all Jetpack Compose icons to fully support TalkBack. Material Design enforces minimum 48x48dp touch targets. Web boundaries utilize strict explicit `<label htmlFor="...">` and `aria-label` tags.
- **Network Defense**: Next.js Edge APIs intercept traffic to apply fixed-window Rate Limiting & injection-blocking `Content-Security-Policy` headers.
- **Database Hardening (RLS)**: Row-Level Security policies strictly enforce identity ownership. Predictive ML schemas prohibit unprotected `WITH CHECK (true)` vectors, offloading insertions securely to internal Service Roles. 

---

## 📦 Getting Started

### Prerequisites
- **Node.js**: v18 or newer
- **Android Studio**: Latest release (Ladybug/Koala)
- **Java Development Kit (JDK)**: Version 17
- **Supabase**: An active [Supabase](https://supabase.com/) project to host the PostgreSQL database, GoTrue Auth layer, and Edge Functions.

### 1. Web Dashboard (Next.js)

1. **Clone & Navigate**:
   ```bash
   git clone https://github.com/ayushmishra-18/Agro-Connect.git
   cd "Agro-Connect/web-dashboard"
   ```

2. **Install Dependencies**:
   ```bash
   npm install
   ```

3. **Configure Environment Variables**:
   Create a `.env.local` file in the root of the `web-dashboard` directory containing your Supabase credentials:
   ```env
   NEXT_PUBLIC_SUPABASE_URL="https://[YOUR_PROJECT_ID].supabase.co"
   NEXT_PUBLIC_SUPABASE_ANON_KEY="your-anon-api-key"
   ```

4. **Launch Local Server**:
   ```bash
   npm run dev
   ```
   *The application will boot and become accessible at `http://localhost:3000`.*

### 2. Android Client (Kotlin)

1. **Import the Project**:
   Launch **Android Studio** and select `Open`. Navigate to the cloned repository and select the `mobile-android` directory. Allow Gradle to execute its initial sync.

2. **Configure SDK & Local Properties**:
   Open the `local.properties` file in the `mobile-android` root and define your Supabase endpoints. These are securely injected into `BuildConfig` during compilation:
   ```properties
   SUPABASE_URL="https://[YOUR_PROJECT_ID].supabase.co"
   SUPABASE_ANKEY="your-anon-api-key"
   ```

3. **Compile and Execute**:
   - Connect a physical Android device (via USB Debugging) or start an Android Virtual Device (AVD).
   - Click the green **Run 'app'** button in the top toolbar. Or, you can execute the command-line equivalent:
     ```bash
     ./gradlew assembleDebug
     ```

---

## 📋 Database Schema Context

> 💡 **Bring Your Own Database:** When deploying locally, you cannot use the production database. Execute the provided [`database_schema.sql`](./database_schema.sql) file inside the SQL Editor of your new empty Supabase project to instantly scaffold the architecture below, including tables, relationships, triggers, and Row Level Security (RLS) policies.

- `u_users` (UUID auth.users relational mapping)
- `u_farmer_profile` & `u_buyer_profile` (Identity & Telemetry data)
- `c_crops` & `c_mandis` (Foundational master records)
- `m_listings`, `m_cart_items`, `m_orders` (Escrow/Payment structure)
- `e_weather_data` & `e_fuel_prices` (Exogenous environmental variables)
- `p_daily_market_prices` & `p_prediction_outputs` (ML Data Ingestion pipelines)

---

## 🤝 Contributing
For feature additions, branch off using a standardized `feature/` taxonomy (e.g., `feature/offline-sync`). Please consult the core issue tracker prior to executing significant architecture forks (especially regarding Subabase edge-functions or local Room persistence graphs). Ensure that code passes frontend ESLint tests and Android `lintRelease` prior to generating Pull Requests.
