'use client';

import { useState } from 'react';
import { Settings, Globe, User, Sprout, MapPin, Bell, Palette } from 'lucide-react';

export default function SettingsPage() {
  const [language, setLanguage] = useState('en');
  const [saved, setSaved] = useState(false);

  function handleSave() {
    setSaved(true);
    setTimeout(() => setSaved(false), 2500);
  }

  return (
    <div style={{ maxWidth: 700 }}>
      {/* Profile Section */}
      <div className="card" style={{ marginBottom: 24 }}>
        <div className="card-header">
          <h3><User size={18} style={{ marginRight: 8, verticalAlign: 'middle' }} />Profile</h3>
        </div>
        <div className="card-body">
          <div className="form-group">
            <label className="form-label">Full Name</label>
            <input className="form-input" placeholder="Enter your name" defaultValue="" />
          </div>
          <div style={{ display: 'flex', gap: 16 }}>
            <div className="form-group" style={{ flex: 1 }}>
              <label className="form-label">Phone Number</label>
              <input className="form-input" placeholder="+91 XXXXX XXXXX" />
            </div>
            <div className="form-group" style={{ flex: 1 }}>
              <label className="form-label">User Type</label>
              <select className="form-select">
                <option value="FARMER">Farmer</option>
                <option value="BUYER">Buyer</option>
              </select>
            </div>
          </div>
        </div>
      </div>

      {/* Location & Crop Preferences */}
      <div className="card" style={{ marginBottom: 24 }}>
        <div className="card-header">
          <h3><Sprout size={18} style={{ marginRight: 8, verticalAlign: 'middle' }} />Farming Preferences</h3>
        </div>
        <div className="card-body">
          <div className="form-group">
            <label className="form-label">
              <MapPin size={14} style={{ marginRight: 4, verticalAlign: 'middle' }} />
              Primary Location
            </label>
            <input className="form-input" placeholder="Village / District / State" />
          </div>
          <div className="form-group">
            <label className="form-label">Primary Crops (up to 3)</label>
            <div style={{ display: 'flex', gap: 8, flexWrap: 'wrap' }}>
              {['Wheat', 'Rice', 'Onion', 'Tomato', 'Potato', 'Soybean', 'Cotton', 'Sugarcane'].map(crop => (
                <button
                  key={crop}
                  className="btn btn-sm btn-secondary"
                  style={{ borderRadius: 100 }}
                >
                  {crop}
                </button>
              ))}
            </div>
          </div>
          <div className="form-group">
            <label className="form-label">Land Size</label>
            <div style={{ display: 'flex', gap: 8, alignItems: 'center' }}>
              <input className="form-input" type="number" placeholder="0" style={{ maxWidth: 120 }} />
              <span style={{ color: 'var(--color-text-secondary)', fontSize: '0.85rem' }}>Acres</span>
            </div>
          </div>
        </div>
      </div>

      {/* Language Settings */}
      <div className="card" style={{ marginBottom: 24 }}>
        <div className="card-header">
          <h3><Globe size={18} style={{ marginRight: 8, verticalAlign: 'middle' }} />Language / भाषा</h3>
        </div>
        <div className="card-body">
          <div style={{ display: 'flex', gap: 12, flexWrap: 'wrap' }}>
            {[
              { code: 'en', name: 'English', native: 'English' },
              { code: 'hi', name: 'Hindi', native: 'हिन्दी' },
              { code: 'mr', name: 'Marathi', native: 'मराठी' },
            ].map(lang => (
              <button
                key={lang.code}
                className={`btn ${language === lang.code ? 'btn-primary' : 'btn-secondary'}`}
                onClick={() => setLanguage(lang.code)}
                style={{ minWidth: 140, flexDirection: 'column', padding: '14px 20px', gap: 2 }}
              >
                <span style={{ fontSize: '1rem', fontWeight: 700 }}>{lang.native}</span>
                <span style={{ fontSize: '0.7rem', opacity: 0.8 }}>{lang.name}</span>
              </button>
            ))}
          </div>
          <p style={{ marginTop: 12, fontSize: '0.75rem', color: 'var(--color-text-secondary)' }}>
            ⓘ Punjabi and Bengali will be available in Phase 2.
          </p>
        </div>
      </div>

      {/* Save Button */}
      <div style={{ display: 'flex', justifyContent: 'flex-end', gap: 12 }}>
        {saved && (
          <div className="badge badge-success" style={{ padding: '10px 16px', fontSize: '0.85rem' }}>
            ✓ Settings saved successfully!
          </div>
        )}
        <button className="btn btn-primary btn-lg" onClick={handleSave}>
          Save Settings
        </button>
      </div>
    </div>
  );
}
