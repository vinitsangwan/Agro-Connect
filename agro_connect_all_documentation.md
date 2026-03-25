# Agro-Connect

## Project Description
Agro-Connect helps farmers in India make informed decisions about when and where to sell their crops by providing 7-day price predictions based on historical data, weather conditions, and fuel prices. It serves as a comprehensive digital assistant for farmers, available as both a web dashboard and an Android application.
A unified platform (Web & Mobile) that:
Buy and Sell Crop and Agriculture related equipment, with unified payment system
Predicts crop prices for the next 7 days using ML models
Analyzes historical data, weather patterns, and fuel prices
Recommends the best time and market to sell crops
Connects farmers to nearby mandis (markets)
Provides weather forecasts and farming advisories
Supports multiple languages (English, Hindi, Punjabi, Bengali, Marathi)

## Product Requirements Document
PRODUCT REQUIREMENTS DOCUMENT (PRD) – AGRO-CONNECT

1. INTRODUCTION AND GOALS

1.1 Project Overview
Agro-Connect is a unified digital platform (Web Dashboard and Android Application) designed to empower Indian farmers by providing data-driven insights for better selling decisions. The core offering revolves around 7-day crop price predictions derived from historical data, weather patterns, and fuel costs, complemented by marketplace functionalities, localized advisories, and mandi discovery.

1.2 Business Goals
1. Increase farmer profitability by reducing selling uncertainty through accurate price predictions.
2. Establish a trusted, unified digital marketplace for agricultural produce and equipment.
3. Achieve high adoption rates (Target: 50,000 MAU by Year 1) by ensuring accessibility across diverse connectivity and hardware profiles.
4. Provide actionable, real-time farming intelligence localized to the user's context.

1.3 Target Audience Profile
The typical user is a farmer with moderate smartphone familiarity, often using a mid-cost Android device with inconsistent rural internet connectivity. The platform must prioritize simplicity, low data usage, and robust offline functionality.

2. PRODUCT FEATURES AND FUNCTIONALITY

2.1 Core Predictive Engine (Price Forecasting)
Feature ID: PFE-001
Description: Provide ML-driven 7-day forecasts for prevailing crop prices at specified mandis or regional averages.
Requirements:
*   Input Data Sources: AGMARKNET, eNAM (crop prices), IMD/OpenWeather (weather), IOCL/Data.gov.in (fuel prices).
*   Model Accuracy Metric: Target Mean Absolute Percentage Error (MAPE) of 8–12%.
*   Update Cadence: Weekly incremental model updates; full retraining every 1–3 months.
*   Output Display: Display price prediction, confidence interval, and recommended optimal selling window (e.g., "Sell between Day 3 and Day 5 for predicted high").

2.2 Mandi Discovery and Market Linkage
Feature ID: MDL-002
Description: Connect farmers to nearby regulated physical markets (Mandis).
Requirements:
*   Location Precision: 50–100 meters GPS accuracy for locating the farmer relative to Mandis.
*   Data Source: Initially curated database based on AGMARKNET data; future integration with live eNAM/AGMARKNET feeds.
*   Data Freshness: Mandi location data refreshed quarterly; daily price information displayed must reflect the previous day's closing data.
*   User Interface: Map view and list view, filtering by crop type.

2.3 Marketplace (Buy/Sell Equipment & Produce) - Phase 2 Feature
Feature ID: MKT-003
Description: Enable users to list and search for agricultural equipment and produce for sale.
Requirements:
*   Listing Categories: Dedicated sections for Crops (Produce) and Equipment.
*   Seller Verification: Mandatory KYC verification for all sellers utilizing the payment gateway.
*   Transaction Handling: Implementation of escrow or split-payment mechanisms (Phase 3) to secure transactions before final delivery confirmation.

2.4 Weather and Farming Advisories
Feature ID: ADV-004
Description: Deliver localized weather updates and actionable farming advice.
Requirements:
*   Weather Data: Hourly/Daily forecasts integrated from IMD/OpenWeather.
*   Advisory Content: Hybrid model combining standardized guidelines with personalization based on user location, crop type, and growth stage.
*   Actionability: Advisories must be clear and directly linked to the user's current farming activity profile.

2.5 Multilingual Support and Localization
Feature ID: LOC-005
Description: Full localization parity across specified regional languages.
Requirements:
*   Supported Languages: English (Default), Hindi, Punjabi, Bengali, Marathi.
*   Localization Scope: All UI elements, static text, system notifications, and template-based ML prediction outputs must be translated.
*   Rollout Plan: MVP includes English, Hindi, Marathi. Punjabi and Bengali follow in Phase 2.

3. TECHNICAL SPECIFICATIONS

3.1 Platform Requirements
*   Web: Responsive design adhering to Material Design principles.
*   Mobile: Native Android application prioritized for reach and offline capability.

3.2 Offline Functionality (Android Only)
Feature ID: OFL-006
Description: Ensure core functionality remains accessible without constant internet.
Requirements:
*   Data Caching: Use Android Room database for structured data (predictions, advisories, saved settings).
*   Sync Mechanism: Utilize Android WorkManager for scheduled background synchronization when connectivity is restored.
*   Critical Refresh Cycle: Cached essential data (prices, weather) must aim to refresh every 6–12 hours.

3.3 Scalability and Performance
The infrastructure must support the following Year-1 targets:
*   Monthly Active Users (MAU): ~50,000
*   Daily Active Users (DAU): ~10,000–15,000
*   Peak Concurrent Users: ~1,500 (Infrastructure capacity designed for 2,000 concurrent users).
*   Peak Request Rate: 100–200 requests per second.

3.4 Security and Compliance (Payments)
Feature ID: SEC-007
Description: Secure handling of financial transactions for the Phase 3 marketplace.
Requirements:
*   Gateway: Integration with an RBI-approved payment gateway supporting UPI.
*   Security Standards: Mandatory compliance with PCI DSS standards.
*   KYC: Mandatory KYC checks for all sellers processing payments.

4. DESIGN AND USABILITY

4.1 Design Guidelines
*   Mobile: Strict adherence to Material Design standards.
*   Web: Responsive design ensuring usability on desktops/tablets used by agricultural cooperative offices.
*   Branding: Color palette dominated by agricultural greens and earth tones.

4.2 Accessibility (WCAG 2.1 Compliance)
Both web and mobile interfaces must comply with WCAG 2.1 Level AA standards, ensuring suitability for users potentially relying on screen readers or requiring high contrast.

4.3 Typography
Use of multilingual-friendly fonts such as Roboto (primary) and Noto Sans to support clean rendering across all localized scripts.

5. DATA GOVERNANCE AND INGESTION

5.1 Data Sources Summary
| Data Type | Source | Update Frequency |
| :--- | :--- | :--- |
| Crop prices | AGMARKNET, eNAM | Daily |
| Weather | IMD, OpenWeather | Hourly / Daily |
| Fuel prices | IOCL, Data.gov.in | Daily |

5.2 Data Ingestion Pipeline
The system requires robust ETL processes to normalize disparate JSON/CSV formats into a unified schema suitable for ML training and real-time serving. Data integrity checks are mandatory upon ingestion due to reliance on external government APIs.

6. PROJECT PHASING AND TIMELINE

6.1 Phase 1 (MVP - 4 to 5 Months)
Focus: Core utility and user onboarding.
Features:
*   Price Prediction Engine (PFE-001)
*   Mandi Discovery (MDL-002 - Read-only price data)
*   Basic Weather and Advisory (ADV-004 - Standardized content)
*   Localization (English, Hindi, Marathi initial support)
*   Offline Functionality (OFL-006)

6.2 Phase 2 (Post-MVP)
Focus: Market Expansion and Deeper Intelligence.
Features:
*   Marketplace Listing (MKT-003 - Buy/Sell equipment and produce, non-payment transactions)
*   Full Language Rollout (Punjabi, Bengali)
*   Advanced Advisory Personalization

6.3 Phase 3 (Full Functionality)
Focus: Monetization and Trust Building.
Features:
*   Integrated Payment System (SEC-007) utilizing UPI and Escrow services.
*   Automated Mandi Data Updates via direct API integration.

6.4 Launch Timing Constraint
The MVP launch should be strategically timed to coincide with the start of a major harvest cycle (ideally February–March for Rabi, or September for Kharif) to maximize immediate utility and adoption.

## Technology Stack
# Agro-Connect Technology Stack Documentation

## 1. Overview and Guiding Principles

The technology stack for Agro-Connect is selected based on the core requirements of reliability, low-latency performance for data ingestion, suitability for rural connectivity, and robust multilingual support.

**Key Design Constraints:**
*   **Offline-First Architecture:** Essential for unreliable rural internet.
*   **Performance & Size:** Prioritize lightweight components for mid-cost Android devices.
*   **Scalability:** Capable of handling 50,000 MAU by Year 1.
*   **Security:** Mandatory compliance for financial transactions (Phase 3).

## 2. Backend and Core Services

The backend must handle complex data processing (ML model serving, data aggregation) and serve as the central hub for all data ingestion and API requests.

| Component | Technology/Tool | Justification |
| :--- | :--- | :--- |
| **Programming Language** | Python 3.10+ | Dominant language for Data Science (ML Libraries) and efficient API development. |
| **Web Framework** | Django / Django REST Framework (DRF) | Provides rapid development, built-in ORM, strong security features, and excellent support for creating secure, scalable APIs necessary for both web and mobile clients. |
| **Database (Primary)** | PostgreSQL | Robust, ACID-compliant relational database ideal for structured data (User profiles, Market data, Transaction logs). Excellent geospatial extension support (PostGIS) if advanced mapping is needed later. |
| **Database (Caching/Session)** | Redis | High-speed in-memory data structure store for caching frequently accessed data (e.g., current weather, static advisories) and managing session data, improving response times for high-volume requests. |
| **ML & Data Processing** | Pandas, NumPy, Scikit-learn, TensorFlow/PyTorch | Standard ML libraries required for ingesting historical data, feature engineering (weather, fuel prices), training, and serving the 7-day price prediction models. |
| **Task Queues & Asynchronous Jobs** | Celery with Redis Broker | Essential for handling long-running, non-blocking tasks such as daily ML model retraining, large-scale data ingestion from government APIs, and scheduled advisory push notifications. |
| **Deployment/Containerization** | Docker, Docker Compose | Ensures environment consistency between development, testing, and production. Simplifies deployment onto cloud infrastructure. |
| **Cloud Infrastructure** | AWS or GCP (Standard Tier) | Offers necessary services (Compute, Database hosting, Storage) to meet the Year 1 concurrency target (2,000 concurrent users). Focus on autoscaling for peak loads. |

## 3. Mobile Application (Android Focus)

Given the user profile (moderate familiarity, mid-cost devices), Android is prioritized, focusing on stability, low battery usage, and robust offline capabilities.

| Component | Technology/Tool | Justification |
| :--- | :--- | :--- |
| **Primary Language** | Kotlin | Modern, preferred language for Android development; offers better safety and conciseness than Java. |
| **UI Framework** | Android Jetpack Components (Compose preferred if budget allows; otherwise standard XML/Views) | Ensures adherence to Material Design guidelines and provides modern, efficient UI rendering. |
| **Architecture Pattern** | MVVM (Model-View-ViewModel) | Separates concerns, facilitates unit testing, and integrates seamlessly with Jetpack libraries like LiveData/Flow for reactive data handling. |
| **Offline Data Persistence** | Room Persistence Library | Provides an abstraction layer over SQLite, crucial for implementing the **offline-first design**. Caches predictions, advisories, and mandi listings locally. |
| **Asynchronous Operations/Sync** | Android WorkManager | Manages guaranteed background work (e.g., data synchronization when connectivity is restored, hourly/daily forecast refreshing) while respecting device battery constraints. |
| **Localization & Typography** | Android Resource Files (.xml), Noto Sans Font Family | Native support for handling multilingual strings (English, Hindi, Punjabi, Bengali, Marathi) and using Noto Sans for high-quality rendering across complex scripts. |

## 4. Web Dashboard (Admin/Secondary Access)

The web platform will serve as an administrative dashboard and a supplementary tool for users with better connectivity.

| Component | Technology/Tool | Justification |
| :--- | :--- | :--- |
| **Frontend Framework** | React.js (or Next.js for SSR benefits) | Highly popular for building complex, interactive Single Page Applications (SPAs). Supports responsive design principles easily. |
| **State Management** | Redux Toolkit or React Context | Manages application state predictably, necessary for displaying complex charts and aggregated market data. |
| **Styling/Design System** | Styled-Components/Tailwind CSS + Shared Component Library | Ensures adherence to the unified brand palette and WCAG 2.1 accessibility standards through utility-first styling or component encapsulation. |

## 5. Data Ingestion and External Services

| Service Area | Technology/Tool | Justification |
| :--- | :--- | :--- |
| **API Integration** | Python `requests` library, Custom Scrapers (BeautifulSoup/Selenium for non-API sources, if necessary) | Used to pull data daily/hourly from AGMARKNET, IMD, OpenWeather, and IOCL sources. |
| **Data Validation/Cleaning** | Pandas/Custom Python scripts | Essential step before data ingestion into PostgreSQL to handle format discrepancies between sources (JSON/CSV) and ensure data quality for ML model training. |
| **Payment Gateway (Phase 3)** | RBI-approved Payment Integrator (e.g., Razorpay/Paytm for India) | Required for UPI integration, escrow management, and handling PCI DSS compliance requirements for secure transaction processing. |
| **Geolocation/Mapping** | Google Maps Platform APIs / OpenStreetMap | Used for locating and displaying nearby Mandis (requiring 50–100m precision). |

## 6. Cross-Cutting Concerns

| Area | Technology/Standard | Requirement Addressed |
| :--- | :--- | :--- |
| **Localization & Internationalization (i18n)** | i18next (Web), Android Resource System (Mobile) | Full support for English, Hindi, Punjabi, Bengali, Marathi. Template-based dynamic translation for ML outputs. |
| **Accessibility** | WCAG 2.1 AA Compliance | Mandatory standard applied to both Web (ARIA attributes) and Android (TalkBack compatibility) interfaces. |
| **Security** | JWT (for API Auth), HTTPS/SSL, Input Sanitization | Standard security protocols. Escrow logic implementation (Phase 3) requires rigorous security auditing. |
| **Monitoring & Logging** | Prometheus/Grafana or Cloud Provider Tools | To track API performance, ML prediction latency, and system health against the targeted 100–200 peak requests/sec. |

## Project Structure
PROJECT STRUCTURE DOCUMENT (PROJECTSTRUCTURE) - AGRO-CONNECT

1. OVERVIEW AND ARCHITECTURE

Agro-Connect employs a microservices-oriented backend architecture, serving both a responsive Web Dashboard (React/Next.js) and a robust Android Application (Kotlin/Jetpack Compose). Data ingestion, ML processing, and core APIs are decoupled to ensure scalability and modularity, aligning with the Year-1 target of 1,500 peak concurrent users.

2. BACKEND STRUCTURE (API & SERVICES)

The core backend (hosted, likely using Python/Django/Flask for ML integration or Java/Spring Boot for enterprise services) is organized by functional domain:

```
/backend
|-- /config                  # Environment variables, configuration files
|-- /docs                    # API specifications (Swagger/OpenAPI)
|-- /services/
|   |-- /auth-service/       # User registration, login, JWT handling (Scalability/Security)
|   |-- /data-ingestion/     # Connectors for AGMARKNET, IMD, IOCL APIs. Handles raw data validation.
|   |-- /ml-prediction-service/ # Core service hosting TensorFlow/PyTorch models for 7-day price forecasting.
|   |-- /market-data-service/ # Aggregates and standardizes price data from ML service and direct sources.
|   |-- /mandis-service/     # Manages static/curated mandi locations and proximity search logic (50-100m precision).
|   |-- /advisory-service/   # Combines static guidelines with personalized, location/weather-based recommendations.
|   |-- /payment-service/    # Handles payment initiation, escrow logic, and KYC integration (Phase 3).
|   |-- /localization-service/ # Manages localization resource loading and template translation mapping for dynamic output.
|
|-- /models                  # Database schema definitions (e.g., CropPriceForecast, UserProfile, Transaction)
|-- /migrations              # Database schema change scripts
|-- Dockerfile               # Containerization definitions for deployment
|-- requirements.txt         # Python dependencies
```

3. MACHINE LEARNING PIPELINE STRUCTURE

The ML component is critical for the 7-day prediction accuracy (target MAPE 8-12%).

```
/ml-prediction-service
|-- /models_archive/         # Stores serialized, versioned model artifacts (.h5, .pkl)
|-- /notebooks/              # Exploratory Data Analysis (EDA) and model prototyping scripts
|   |-- 01_eda_price_analysis.ipynb
|   |-- 02_baseline_model_test.ipynb
|-- /pipelines/
|   |-- training_pipeline.py # Orchestrates data fetching, feature engineering, training, and validation (MAPE, MAE, R^2).
|   |-- retraining_scheduler.py # Manages weekly incremental updates and 1-3 month full retraining cycles.
|-- /features/               # Feature engineering scripts (e.g., time-series lags, weather interaction terms)
|-- /metrics/                # Custom metric calculation scripts
|-- config.yaml              # ML configuration (hyperparameters, feature importance thresholds)
```

4. MOBILE APPLICATION (ANDROID) STRUCTURE

The Android app prioritizes offline-first capability, low data usage, and adherence to Material Design principles (WCAG 2.1 compliance).

```
/mobile-android
|-- /app/
|   |-- /src/
|       |-- /main/
|           |-- /java/com/agroconnect/
|               |-- /data/              # Repository layer, Remote Data Sources (Retrofit)
|               |-- /db/                # Local persistence layer (Room Database implementation for offline-first caching)
|               |-- /di/                # Dependency Injection modules (Hilt/Koin)
|               |-- /models/            # Data classes (serialized/database entities)
|               |-- /ui/
|               |   |-- /screens/       # Top-level navigation destinations
|               |   |-- /components/    # Reusable UI elements adhering to shared Design System
|               |   |-- /viewmodels/    # Business logic handlers
|               |-- /utils/
|                   |-- ConnectivityMonitor.kt # Checks for network access
|                   |-- SyncWorker.kt        # Android WorkManager implementation for background sync
|                   |-- LocaleHelper.kt      # Handles dynamic language switching (EN, HI, PN, BN, MR)
|               |-- AndroidManifest.xml
|               |-- themes.xml          # Earth tone/Green palette definitions
|
|-- /localization/
|   |-- values-hi/values-pa/values-bn/values-mr/ # Resource files for all 5 languages, including system/template strings.
|
|-- build.gradle (app/module)
```

5. WEB DASHBOARD STRUCTURE

A responsive interface utilizing a shared design system for consistency with the mobile app.

```
/web-dashboard
|-- /public/                   # Static assets (Logo, Favicons)
|-- /src/
|   |-- /api/                  # Service calls to Backend APIs
|   |-- /assets/               # Global styling and typography definitions (Noto Sans/Roboto)
|   |-- /components/           # Shared, atomic UI components (Design System library)
|   |-- /pages/                # Route-based components (e.g., /dashboard, /market-lookup, /settings)
|   |-- /store/                # State management (e.g., Redux/Zustand store slices)
|   |-- /localization/         # i18n configuration and translation files (.json)
|   |-- styles/                # Global CSS/SCSS definitions (Responsive design setup)
|-- next.config.js             # Next.js specific configuration
|-- package.json
```

6. DATA INGESTION AND STORAGE

Data flow is managed centrally via the Data Ingestion Service, storing processed, historical, and forecast data in separate optimized stores.

```
/data-storage/
|-- /raw_landing_zone/         # Temporary storage for unprocessed daily/hourly API pulls (CSV/JSON from IMD, AGMARKNET)
|-- /processed_warehouse/      # Cleaned, normalized data used for ML training (e.g., Feature Store)
|-- /database_main/            # Primary transactional database (PostgreSQL/MySQL)
|   |-- ml_forecast_cache/     # Read-optimized table for serving 7-day predictions (Refreshed daily/hourly)
|   |-- mandi_location_master/ # Curated and government-sourced mandi coordinates (Quarterly refresh)
|-- /queueing_system/          # Message broker for async tasks (e.g., Kafka/RabbitMQ for handling large ingestion batches)
```

## Database Schema Design
AGRO-CONNECT: SCHEMA DESIGN DOCUMENT

1. INTRODUCTION AND SCOPE

This document details the logical and physical schema design for the Agro-Connect platform, covering the central data models required to support crop price prediction, market discovery, user management, and the future marketplace functionality. The schema prioritizes data integrity, multilingual support, and performance suitable for rural connectivity constraints (offline synchronization requirements).

2. DATABASE ARCHITECTURE OVERVIEW

The platform will utilize a relational database management system (RDBMS) for core transactional data (Users, Transactions, Mandis) to ensure ACID compliance, particularly crucial for financial integrations (Phase 3). A NoSQL or document store might be considered for highly unstructured advisory content or high-volume, less critical log data, but the core schema below focuses on the RDBMS structure.

Target RDBMS: PostgreSQL (Chosen for robust support of JSONB fields, advanced indexing, and strong transactional integrity).

3. CORE ENTITY SCHEMAS (Logical Design)

3.1. USER MANAGEMENT (U_*)

Schema: U_USERS
Description: Stores primary user profiles (Farmers, Buyers, potentially Admin/Advisors).
| Field Name | Data Type | Constraints | Description |
|---|---|---|---|
| user_id | SERIAL/UUID | PK, NOT NULL | Unique User Identifier |
| user_type | VARCHAR(20) | NOT NULL, CHECK | 'FARMER', 'BUYER', 'ADMIN' |
| phone_number | VARCHAR(15) | UNIQUE, NOT NULL | Primary contact number (critical for login/OTP) |
| password_hash | VARCHAR(255) | NOT NULL | Hashed password |
| first_name | VARCHAR(100) | | |
| last_name | VARCHAR(100) | | |
| current_language_code | CHAR(5) | NOT NULL | e.g., 'en', 'hi', 'pa' (Localization scope) |
| registration_date | TIMESTAMP WITH TIME ZONE | NOT NULL | |
| is_kyc_verified | BOOLEAN | DEFAULT FALSE | Required for Phase 3 marketplace sellers |
| last_sync_timestamp | TIMESTAMP WITH TIME ZONE | | For offline/sync tracking |

Schema: U_FARMER_PROFILE (Extension of U_USERS for Farmer-specific data)
| Field Name | Data Type | Constraints | Description |
|---|---|---|---|
| user_id | UUID | FK (U_USERS) | Link to user |
| primary_location_lat | DECIMAL(9,6) | | Farmer's primary location for hyperlocal alerts |
| primary_location_lon | DECIMAL(9,6) | | |
| preferred_crop_id | INT | FK (C_CROPS) | Primary crop of interest |
| land_size_acres | DECIMAL(10,2) | | |

3.2. CROP AND MARKET DATA (C_*)

Schema: C_CROPS
Description: Master list of crops supported by the prediction engine.
| Field Name | Data Type | Constraints | Description |
|---|---|---|---|
| crop_id | SERIAL | PK, NOT NULL | |
| crop_name_en | VARCHAR(100) | NOT NULL | Base name (English) |
| name_localization | JSONB | NOT NULL | Stores localized names: {"hi": "...", "pn": "..."} |

Schema: C_MANDIS
Description: List of physical market locations. Data source: AGMARKNET curation (Monthly/Quarterly refresh).
| Field Name | Data Type | Constraints | Description |
|---|---|---|---|
| mandi_id | SERIAL | PK, NOT NULL | |
| mandi_name | VARCHAR(255) | NOT NULL | |
| state_code | CHAR(2) | NOT NULL | ISO State Code |
| district_name | VARCHAR(100) | | |
| latitude | DECIMAL(9,6) | NOT NULL | 50-100m precision required |
| longitude | DECIMAL(9,6) | NOT NULL | |
| last_updated | DATE | NOT NULL | Date the static location data was refreshed |

3.3. PRICE PREDICTION DATA (P_*)

Schema: P_DAILY_MARKET_PRICES (Historical & Real-time data ingestion)
Description: Stores granular, time-series market price data used for model training and current reference.
| Field Name | Data Type | Constraints | Description |
|---|---|---|---|
| price_id | BIGSERIAL | PK, NOT NULL | |
| crop_id | INT | FK (C_CROPS) | |
| mandi_id | INT | FK (C_MANDIS) | Source Market |
| date | DATE | NOT NULL | Date of record |
| commodity_type | VARCHAR(50) | NOT NULL | e.g., 'Modal Price', 'Min Price' |
| price_per_quintal | DECIMAL(10,2) | NOT NULL | Price in INR |
| data_source | VARCHAR(50) | | e.g., 'AGMARKNET', 'eNAM' |
| ingested_at | TIMESTAMP WITH TIME ZONE | NOT NULL | When the data hit the system |
| UNIQUE | (crop_id, mandi_id, date) | | Prevents duplicate daily entries |

Schema: P_PREDICTION_OUTPUTS
Description: Stores the 7-day forecasts generated by the ML models. Stored separately from raw data for performance.
| Field Name | Data Type | Constraints | Description |
|---|---|---|---|
| prediction_id | BIGSERIAL | PK, NOT NULL | |
| crop_id | INT | FK (C_CROPS) | |
| mandi_id | INT | FK (C_MANDIS) | Predicted Market |
| prediction_date | DATE | NOT NULL | Date the prediction was generated (Model Run Date) |
| forecast_day_index | INT | NOT NULL | 1 (tomorrow) through 7 |
| forecast_date | DATE | NOT NULL | The actual date the price applies to |
| predicted_price | DECIMAL(10,2) | NOT NULL | Forecasted price |
| model_version | VARCHAR(50) | | Tracks which model run created this result |
| directional_accuracy_score | DECIMAL(5,4) | | Metric snapshot at time of generation |
| UNIQUE | (crop_id, mandi_id, prediction_date, forecast_day_index) | | |

3.4. EXTERNAL DATA SOURCES (E_*)

Schema: E_WEATHER_DATA
Description: Hourly/Daily weather data ingestion (IMD/OpenWeather). Designed for high volume caching for offline access.
| Field Name | Data Type | Constraints | Description |
|---|---|---|---|
| weather_id | BIGSERIAL | PK, NOT NULL | |
| location_lat | DECIMAL(9,6) | NOT NULL | Geo-coordinate for weather reading |
| location_lon | DECIMAL(9,6) | NOT NULL | |
| recorded_at | TIMESTAMP WITH TIME ZONE | NOT NULL | Time of observation/forecast start |
| temp_celsius_max | DECIMAL(4,1) | | |
| precipitation_mm | DECIMAL(5,2) | | |
| wind_speed_kph | DECIMAL(4,1) | | |
| condition_code | INT | | Link to standardized weather codes |
| ingested_at | TIMESTAMP WITH TIME ZONE | NOT NULL | |

Schema: E_FUEL_PRICES
Description: Daily fuel price tracking (Diesel/Petrol).
| Field Name | Data Type | Constraints | Description |
|---|---|---|---|
| fuel_price_id | BIGSERIAL | PK, NOT NULL | |
| date | DATE | NOT NULL | |
| fuel_type | VARCHAR(10) | NOT NULL | e.g., 'Diesel', 'Petrol' |
| price_per_liter | DECIMAL(6,2) | NOT NULL | |
| location_id | INT | FK (C_MANDIS) | Or region/state level granularity |

3.5. ADVISORY AND LOCALIZATION (A_*)

Schema: A_ADVISORIES
Description: Standardized, trusted farming guidelines.
| Field Name | Data Type | Constraints | Description |
|---|---|---|---|
| advisory_id | SERIAL | PK, NOT NULL | |
| advisory_type | VARCHAR(50) | NOT NULL | e.g., 'Fertilization', 'Pest Control', 'Sowing' |
| crop_id | INT | FK (C_CROPS) | NULLABLE if general advisory |
| state_scope | VARCHAR(2) | | Optional filter for state-specific advice |
| generated_content | JSONB | NOT NULL | Structured, actionable content body |

Schema: L_TRANSLATIONS (For interface elements and dynamic template phrases)
Description: Central repository for all localized strings (Localization scope: EN, HI, PN, BN, MR).
| Field Name | Data Type | Constraints | Description |
|---|---|---|---|
| translation_key | VARCHAR(255) | PK, NOT NULL | Unique application key (e.g., 'btn_login') |
| en | VARCHAR(512) | NOT NULL | English base string |
| hi | VARCHAR(512) | | Hindi translation |
| pn | VARCHAR(512) | | Punjabi translation |
| bn | VARCHAR(512) | | Bengali translation |
| mr | VARCHAR(512) | | Marathi translation |

4. PHASED FEATURE MAPPING AND SCHEMA IMPACT

| Feature Phase | Primary Schema Modules Affected | Key Data Interactions |
|---|---|---|
| **MVP (Phase 1)** | U_*, C_CROPS, C_MANDIS, P_DAILY_MARKET_PRICES, P_PREDICTION_OUTPUTS, E_WEATHER_DATA, L_TRANSLATIONS | User registration, Price Prediction retrieval (cached), Mandi listing (static lookup), Weather display. Offline access prioritized via local Room DB mirroring cached data. |
| **Phase 2 (Marketplace)** | U_*, M_LISTINGS, M_TRANSACTIONS (Initial Structure) | Introduction of marketplace schema to handle Buy/Sell listings. |
| **Phase 3 (Payments/Escrow)** | U_*, M_TRANSACTIONS (Full), PAYMENTS_LEDGER | Introduces KYC tracking in U_USERS. New schema for secure transaction tracking, escrow status, and linking to the external UPI gateway via a secure ledger table. |

5. MARKETPLACE SCHEMA (Phase 2 & 3)

Schema: M_LISTINGS
Description: Items listed for sale (Crops or Equipment).
| Field Name | Data Type | Constraints | Description |
|---|---|---|---|
| listing_id | BIGSERIAL | PK, NOT NULL | |
| seller_user_id | UUID | FK (U_USERS), NOT NULL | |
| item_type | VARCHAR(20) | NOT NULL | 'CROP' or 'EQUIPMENT' |
| crop_id | INT | FK (C_CROPS) | NULLABLE if Equipment |
| equipment_details | JSONB | | Details specific to equipment (model, condition) |
| quantity | DECIMAL(10,2) | NOT NULL | e.g., Quintals of crop, or units of equipment |
| unit_of_measure | VARCHAR(20) | | e.g., 'Quintal', 'Unit' |
| listed_price | DECIMAL(10,2) | NOT NULL | Price per unit |
| listing_status | VARCHAR(20) | NOT NULL | 'ACTIVE', 'SOLD', 'PENDING_PAYMENT' |
| created_at | TIMESTAMP WITH TIME ZONE | NOT NULL | |

Schema: M_TRANSACTIONS (Handles negotiation and final sale confirmation)
| Field Name | Data Type | Constraints | Description |
|---|---|---|---|
| transaction_id | BIGSERIAL | PK, NOT NULL | |
| listing_id | BIGINT | FK (M_LISTINGS), NOT NULL | |
| buyer_user_id | UUID | FK (U_USERS), NOT NULL | |
| agreed_quantity | DECIMAL(10,2) | NOT NULL | Final quantity traded |
| agreed_price_total | DECIMAL(12,2) | NOT NULL | Final agreed price |
| escrow_status | VARCHAR(30) | NOT NULL | e.g., 'INITIATED', 'FUNDS_HELD', 'RELEASED', 'FAILED' |
| transaction_date | TIMESTAMP WITH TIME ZONE | NOT NULL | Date agreement finalized |

Schema: PAYMENTS_LEDGER (Phase 3 - Records financial movements)
| Field Name | Data Type | Constraints | Description |
|---|---|---|---|
| payment_entry_id | BIGSERIAL | PK, NOT NULL | |
| transaction_id | BIGINT | FK (M_TRANSACTIONS), NOT NULL | |
| gateway_ref_id | VARCHAR(255) | UNIQUE | Reference ID from external Payment Gateway |
| payment_type | VARCHAR(50) | NOT NULL | 'DEBIT' (Buyer payment), 'CREDIT' (Seller payout) |
| amount | DECIMAL(12,2) | NOT NULL | |
| fee_amount | DECIMAL(10,2) | DEFAULT 0.00 | Gateway/Platform commission |
| processed_at | TIMESTAMP WITH TIME ZONE | NOT NULL | |
| status | VARCHAR(50) | NOT NULL | 'SUCCESS', 'PENDING', 'FAILED' |

6. DATA CONSISTENCY AND OFFLINE STRATEGY CONSIDERATIONS

Offline functionality requires the mobile application (Room DB) to cache data derived from:
1.  `P_PREDICTION_OUTPUTS`: Cached for 7 days minimum.
2.  `C_MANDIS` & `C_CROPS`: Cached for monthly/quarterly refresh cycles.
3.  `A_ADVISORIES`: Cached based on user profile/location.

The `last_sync_timestamp` in `U_USERS` helps the backend prioritize updates for that specific device upon reconnection, maximizing the utility of the limited network window.

## User Flow
USERFLOW DOCUMENTATION: AGRO-CONNECT

VERSION: 1.0
DATE: October 26, 2023
STATUS: Draft

---

## 1. Overview and Goal

This document details the primary user flows (journeys) for the Agro-Connect platform (Web Dashboard and Android Application). The objective is to ensure smooth navigation, optimal data presentation, and high usability for the target user: farmers with moderate digital literacy operating under variable connectivity conditions. Flows prioritize the core value proposition: delivering timely crop price predictions and market insights.

---

## 2. Primary User Flows (MVP Focus)

The MVP prioritizes Price Prediction, Mandi Discovery, and Advisory Access. Marketplace and Payment flows are noted but deferred to later phases.

### 2.1 Flow A: Initial Setup and Preference Configuration (First-Time User)

**Goal:** Onboarding the farmer and setting critical baseline preferences (language, primary location).

| Step | User Action | System Response (Web/Mobile) | Wireframe/Screen Notes | Interaction Pattern |
| :--- | :--- | :--- | :--- | :--- |
| A1 | Launch App/Visit Web | Splash Screen / Welcome Modal | Branding introduction. | Load time consideration (Offline Sync checks cached data first). |
| A2 | Select Preferred Language | Language Selection Screen | Options: English, Hindi, Marathi (MVP languages). Large, clear buttons. | Mandatory selection before proceeding. Supports offline language pack download. |
| A3 | Location/Mandi Selection | Location Setup Screen | Prompt to allow GPS access OR manually enter Village/District. | If GPS denied, manual entry is mandatory for localized data (Weather/Mandi). |
| A4 | Primary Crop Selection | Crop Profile Setup | List of common regional crops. Allows selection of up to 3 primary crops. | Used to tailor initial dashboard views and alerts. |
| A5 | Completion | Dashboard Home Screen | Initial data loads (cached if offline). Prompt for system sync notification (e.g., \"Data refreshing upon connection\"). | Successful onboarding moves to the core usage flows. |

### 2.2 Flow B: Checking 7-Day Crop Price Prediction (Core Value)

**Goal:** Farmer checks the predicted selling price range for their primary crop for the upcoming week.

| Step | User Action | System Response (Web/Mobile) | Wireframe/Screen Notes | Interaction Pattern |
| :--- | :--- | :--- | :--- | :--- |
| B1 | Navigate to Prediction Module | Home Dashboard link: \"Price Forecasts\" | Card view showing selected primary crops. Tapping one initiates the detailed view. | High visibility on the home screen. Responsive design for easy tapping. |
| B2 | Select Specific Crop/Variety | Detailed Prediction Screen | Defaults to today's date comparison. Displays 7-day forecast chart. | Chart uses green/red color coding for upward/downward trends. |
| B3 | Review Prediction Details | Price Prediction Details View | Displays predicted Min/Max Price (per quintal/kg), Confidence Interval (e.g., 88%), and the key influencing factors (e.g., \"Upcoming rain may suppress short-term prices\"). | Prediction data is sourced from local cache (Room DB) if offline. |
| B4 | Compare with Local Mandi Prices | Toggle/Tab Switch within Detail Screen | Shows predicted price vs. current average price across nearby Mandis for the selected dates. | Comparison visualization to aid selling decision. |
| B5 | Save Recommendation | \"Set Alert\" or \"Save Decision\" button | Confirmation modal: \"Alert set for Price X on Date Y.\" | Triggers a local notification setup (WorkManager job) if price conditions are met. |

### 2.3 Flow C: Discovering Nearby Mandis and Current Rates

**Goal:** Identify the closest and best-performing markets for immediate sale.

| Step | User Action | System Response (Web/Mobile) | Wireframe/Screen Notes | Interaction Pattern |
| :--- | :--- | :--- | :--- | :--- |
| C1 | Navigate to Mandi Finder | Home Dashboard link: \"Nearby Markets\" | Displays a map view centered on the user's current location (or last known location). | Map pins show mandi locations (50-100m precision). |
| C2 | Filter Mandis (Optional) | Filter Overlay | Filters by: Crop Type, Distance Radius, Last Traded Price Range. | Filters must be simple; large selection handles preferred for low-dexterity users. |
| C3 | Select a Mandi on Map/List | Mandi Detail Card/Popup | Shows Mandi Name, Distance, Today's Closing Prices for relevant crops, and Fuel Price proximity indicator. | List view defaults to sorting by distance. |
| C4 | Get Directions | \"Navigate Here\" button | Launches the device's default map application (Google Maps/etc.) pre-filled with Mandi coordinates. | External integration for navigation simplicity. |
| C5 | Review Mandi Advisory | Scrollable section in detail card | Relevant advisories regarding specific mandi operations (e.g., \"Mandi closed Mondays\"). | Data synthesized from curated database. |

### 2.4 Flow D: Accessing Farming Advisories

**Goal:** Receive timely, location-specific advice on farming practices.

| Step | User Action | System Response (Web/Mobile) | Wireframe/Screen Notes | Interaction Pattern |
| :--- | :--- | :--- | :--- | :--- |
| D1 | Navigate to Advisory Hub | Home Dashboard link: \"Farming Tips\" | Displays categorized advisories (e.g., Irrigation, Pest Control, Fertilization). | Color coding to indicate urgency/relevance. |
| D2 | View Personalized Recommendations | Top Priority Section | Shows 1-3 high-priority alerts based on current weather/crop stage (e.g., \"Pest Alert: Aphids suspected in your area. Apply Neem Oil.\"). | This section utilizes localized data (advisory_content_and_customization). |
| D3 | Browse General Guidelines | Category Selection | Farmer selects Crop Type -> Growth Stage. | Drill-down navigation. Content must be available offline (cached PDFs/text). |
| D4 | Review Advisory Content | Full Article View | Displays text, simple diagrams (optimized for low bandwidth), and contact info for local agricultural extension officer (if available). | WCAG compliance (text size adjustability) critical here. |

---

## 3. Platform-Specific Interaction Patterns

### 3.1 Offline Functionality (Android Priority)

*   **Data Caching:** Upon successful initial sync, all critical data (7-day predictions, Mandi list, last 30 days of weather, advisory library) is stored locally (Room DB/Filesystem).
*   **User Interaction:** If offline, the app clearly displays a persistent banner: \"Offline Mode. Data last updated [Timestamp].\" System actions like saving a decision are queued locally.
*   **Synchronization:** WorkManager orchestrates background sync when connectivity is restored (every 6-12 hours for core data refresh). Sync status is communicated via a small, dismissible notification when connectivity returns.

### 3.2 Multilingual Handling (Localization)

*   **Interface Elements:** All static text, labels, and system messages are pulled from localized resource files based on the A2 selection.
*   **Dynamic Content Templates:** ML predictions (B3) use templated strings:
    *   English: \"The projected price for [CropName] will be between [MinPrice] and [MaxPrice] on [Date].\"
    *   Hindi: \"[Date] को [CropName] का अनुमानित मूल्य [MinPrice] और [MaxPrice] के बीच रहेगा।\"
*   **Typography:** Consistent use of Noto Sans for readability across all supported scripts (Devanagari, Gurmukhi, Bengali).

### 3.3 Web Dashboard Considerations

*   The web dashboard follows responsive design principles but is primarily aimed at secondary users (e.g., family members assisting with record-keeping) or farmers accessing on shared computers.
*   Interaction patterns mirror the mobile app for consistency, but reliance on persistent internet access is assumed.

---

## 4. Deferred Flows (Phase 2 & 3)

These flows are documented but are intentionally minimal in the MVP design to meet the 4-5 month timeline.

### 4.1 Flow E: Crop Marketplace Listing (Phase 2)

User lists crops for sale. Requires KYC completion (Phase 3 dependency).

*   **Key Interaction:** Guided upload process for crop quantity, quality grade, and desired selling price.
*   **Wireframe Note:** Includes pre-filling known farmer data (crop type) from the initial setup.

### 4.2 Flow F: Transaction Initiation and Payment (Phase 3)

Buyer expresses interest, initiates payment via integrated UPI.

*   **Key Interaction:** Secure Hand-off to RBI-approved gateway. Escrow service UI must clearly explain fund holding status (Pending Inspection, Released to Seller).
*   **Security Note:** Must enforce and visually confirm KYC status before allowing listing/buying transactions.

## Styling Guidelines
AGRO-CONNECT STYLING GUIDELINES DOCUMENT

1.0 INTRODUCTION

This document outlines the visual identity, design language, and styling principles for the Agro-Connect platform (Web Dashboard and Android Application). The goal is to establish a consistent, trustworthy, and accessible user experience tailored to the specific needs of Indian farmers, prioritizing clarity, low cognitive load, and performance on low-specification devices.

2.0 BRAND IDENTITY AND COLOR PALETTE

2.1 Brand Persona
Agro-Connect embodies Trust, Growth, and Accessibility. The visual identity must convey reliability (like established government systems) while remaining modern and intuitive for moderate technology users.

2.2 Primary Color Palette

The palette is inspired by agriculture—rich earth, vibrant crops, and clear skies.

| Name | Hex Code | Usage | Rationale |
| :--- | :--- | :--- | :--- |
| Primary Green (Growth) | #1B5E20 | Primary CTAs, success indicators, branding elements. | Represents healthy crops and reliability. |
| Secondary Green (Accent) | #689F38 | Secondary actions, highlights, positive feedback. | Lighter tone for contrast and interaction states. |
| Earth Brown (Base) | #4E342E | Backgrounds for card headers, text emphasis (especially for Hindi/Marathi localization). | Grounding tone, reminiscent of fertile soil. |
| Warning Yellow | #FFC107 | Price volatility alerts, temporary advisories. | Standard warning color; high visibility against primary colors. |
| Danger Red | #D32F2F | Critical alerts (e.g., pest warnings, payment failure). | High urgency indicator. |

2.3 Neutral Palette (Grayscale)

Crucial for ensuring readability, especially in areas with low light or high glare, and accommodating multilingual text density.

| Name | Hex Code | Usage |
| :--- | :--- | :--- |
| Background White | #FFFFFF | Main screen backgrounds (Mobile prioritized). |
| Surface Gray | #F5F5F5 | Card backgrounds, separated sections (Web/Mobile). |
| Primary Text | #212121 | Body text, critical data points. |
| Secondary Text | #757575 | Hints, metadata, captions (WCAG contrast compliance required). |

3.0 TYPOGRAPHY

Typography must support multilingual content (English, Hindi, Punjabi, Bengali, Marathi) while maintaining legibility on smaller, lower-resolution Android screens.

3.1 Font Family Selection
The design system relies on fonts optimized for both Latin and Indic scripts.

*   **Primary Font (Latin Scripts):** Roboto (For Android, adhering to Material Design standards).
*   **Secondary Font (Indic Scripts):** Noto Sans (Ensures consistent glyph coverage and appearance across Hindi, Punjabi, Bengali, and Marathi).
*   *Implementation Note:* Dynamic text rendering must intelligently switch or utilize a unified typeface like Noto Sans across both platforms where possible for consistency, relying on system defaults if necessary, but prioritizing Roboto/Noto Sans compatibility.

3.2 Text Scale and Hierarchy

The scale must be generous to aid readability for users with potentially varying visual acuity and on variable screen densities.

| Style | Web (px/rem) | Mobile (sp) | Usage |
| :--- | :--- | :--- | :--- |
| H1 (Screen Title) | 32px / 2rem | 24sp | Primary view titles (e.g., Home Dashboard). |
| H2 (Section Header) | 20px / 1.25rem | 18sp | Major content grouping (e.g., Price Prediction Card). |
| Body Large | 16px / 1rem | 16sp | Key data points, primary descriptive text. |
| Body Default | 14px / 0.875rem | 14sp | Standard paragraph text, list items. |
| Caption/Metadata | 12px / 0.75rem | 12sp | Timestamps, sources, small notes (must meet WCAG contrast). |

4.0 UI/UX PRINCIPLES (Platform Specific)

4.1 Unified Design System (Shared Principles)

*   **Simplicity First:** Minimize steps for core tasks (checking prediction, viewing local mandi). Avoid complex gestures.
*   **Data Density Optimization:** For the Web Dashboard, allow moderate density. For the Android app, prioritize high readability over high density to accommodate low-resolution displays and reduced user focus time.
*   **Multilingual Consistency:** All text fields, buttons, and labels must accommodate the longest translation (e.g., Punjabi or Bengali equivalents) without truncation, adjusting padding dynamically.

4.2 Android Application Guidelines (Offline-First & Low-End Devices)

*   **Adherence to Material Design:** Strictly follow Material Design 3 principles for navigation, components, and feedback mechanisms to leverage established Android patterns.
*   **Offline State Management:** All key components (Prediction, Advisories, Mandi List) must gracefully display cached data with a clear "Last Updated: [Timestamp]" indicator. Use the Earth Brown color sparingly to highlight offline status if necessary.
*   **Component Simplicity:** Prefer standard Android widgets (Buttons, Floating Action Buttons (FABs), Cards) over custom, resource-intensive animations or complex view hierarchies that strain mid-cost processors.
*   **Touch Target Size:** Minimum touch targets must be 48x48dp to ensure reliable interaction, especially for users who may not have the latest high-precision touchscreens.

4.3 Web Dashboard Guidelines (Responsiveness & Accessibility)

*   **Responsive Design:** Implement a mobile-first approach, ensuring the layout reflows logically from smaller viewports to larger desktop screens. Prioritize content flow over complex grid structures on mobile views.
*   **Data Visualization:** Charts (used for historical price comparison) must be clear and highly contrastive. Avoid intricate gradients or excessive visual flair that may distract from core numerical data.
*   **WCAG 2.1 AA Compliance:** Mandatory focus indicators must be clearly visible (using Primary Green borders) for keyboard navigation, especially crucial for accessibility and compliance in professional/government contexts.

5.0 ICONOGRAPHY

Icons must be simple, universally recognizable icons that translate well across cultures and languages, utilizing a consistent stroke weight (e.g., Material Icons Outlined or a similarly simple set).

| Icon Concept | Recommended Style | Usage Context |
| :--- | :--- | :--- |
| Price Prediction | Line chart or upward arrow | Main prediction screen access. |
| Weather | Sun/Cloud/Rain droplet | Weather advisory section. |
| Mandi Location | Map pin | Market discovery service. |
| Buy/Sell (Marketplace) | Exchange arrows or basket | Phase 2 marketplace access. |
| Advisory | Lightbulb or Book | Farming guidance section. |

6.0 DATA PRESENTATION STANDARDS

6.1 Price Prediction Output

*   **Format:** Must display the predicted price range clearly, not just a single point estimate. E.g., "₹ 1850 – ₹ 1920 per Quintal."
*   **Confidence Indicator:** Use a small color-coded pill (Secondary Green for high confidence, Warning Yellow for low confidence, based on model MAPE variance) next to the prediction to build trust.
*   **Units:** All commodity prices must clearly state the unit (e.g., Per Quintal, Per Kg) adjacent to the value, standardized across all localized interfaces.

6.2 Payment Interface (Phase 3)

*   **Security Reassurance:** Use standard lock icons and clear text mentioning UPI/RBI compliance near the payment entry fields.
*   **Escrow Transparency:** Clearly label funds held in escrow versus immediately disbursed funds using distinct visual language (e.g., a small shield icon for escrowed amounts).

7.0 ACCESSIBILITY AND INTERNATIONALIZATION (i18n)

7.1 Text Expansion Handling

Due to the variation in script length (e.g., Hindi vs. English), UI elements must be built with flexible padding to accommodate 20–30% text expansion without layout breakage. Elements that cannot expand sufficiently (like fixed-width buttons in the MVP) must use truncated, context-specific text that is validated during localization QA.

7.2 Contrast Ratios

*   Normal Text to Background: Minimum 4.5:1 ratio (Adhering to WCAG AA).
*   Large Text (18pt+): Minimum 3:1 ratio.

7.3 Input Fields

All input fields (especially for KYC/seller registration in Phase 2/3) must have clear, persistent labels (not just placeholder text) that remain visible when the field is active, aiding users with lower technological familiarity.
