'use client';

import { useState, useEffect } from 'react';
import { supabase } from '@/lib/supabase';
import {
  BookOpen, AlertTriangle, Droplets, Bug, Sprout, CloudRain,
  Archive, BarChart3, FlaskConical, Filter,
} from 'lucide-react';

interface Advisory {
  advisory_id: number;
  advisory_type: string;
  crop_id: number | null;
  state_scope: string | null;
  title_en: string;
  content_en: string;
  urgency: string;
  created_at: string;
}

interface Crop {
  crop_id: number;
  crop_name_en: string;
}

const typeConfig: Record<string, { icon: React.ReactNode; color: string; bg: string }> = {
  'Irrigation': { icon: <Droplets size={20} />, color: '#1565C0', bg: '#E3F2FD' },
  'Pest Control': { icon: <Bug size={20} />, color: '#D32F2F', bg: '#FFEBEE' },
  'Fertilization': { icon: <Sprout size={20} />, color: '#2E7D32', bg: '#E8F5E9' },
  'Weather': { icon: <CloudRain size={20} />, color: '#E65100', bg: '#FFF3E0' },
  'Sowing': { icon: <Sprout size={20} />, color: '#1B5E20', bg: '#E8F5E9' },
  'Storage': { icon: <Archive size={20} />, color: '#4E342E', bg: '#EFEBE9' },
  'Market Intelligence': { icon: <BarChart3 size={20} />, color: '#1565C0', bg: '#E3F2FD' },
  'Soil Health': { icon: <FlaskConical size={20} />, color: '#6D4C41', bg: '#EFEBE9' },
};

export default function AdvisoriesPage() {
  const [advisories, setAdvisories] = useState<Advisory[]>([]);
  const [crops, setCrops] = useState<Crop[]>([]);
  const [typeFilter, setTypeFilter] = useState('');
  const [urgencyFilter, setUrgencyFilter] = useState('');
  const [loading, setLoading] = useState(true);
  const [expandedId, setExpandedId] = useState<number | null>(null);

  useEffect(() => {
    loadData();
  }, []);

  async function loadData() {
    const [{ data: advData }, { data: cropData }] = await Promise.all([
      supabase.from('a_advisories').select('*').order('urgency'),
      supabase.from('c_crops').select('crop_id, crop_name_en'),
    ]);
    setAdvisories(advData || []);
    setCrops(cropData || []);
    setLoading(false);
  }

  const types = [...new Set(advisories.map(a => a.advisory_type))];
  const filtered = advisories.filter(a => {
    if (typeFilter && a.advisory_type !== typeFilter) return false;
    if (urgencyFilter && a.urgency !== urgencyFilter) return false;
    return true;
  });

  const getCropName = (id: number | null) => {
    if (!id) return 'General';
    return crops.find(c => c.crop_id === id)?.crop_name_en || 'Unknown';
  };

  const urgencyOrder: Record<string, number> = { CRITICAL: 0, HIGH: 1, MEDIUM: 2, LOW: 3 };
  const sorted = [...filtered].sort((a, b) => (urgencyOrder[a.urgency] ?? 99) - (urgencyOrder[b.urgency] ?? 99));

  if (loading) {
    return (
      <div style={{ display: 'flex', flexDirection: 'column', gap: 16 }}>
        {[1, 2, 3, 4].map(i => (
          <div key={i} className="card" style={{ padding: 24 }}>
            <div className="skeleton" style={{ height: 24, width: '60%', marginBottom: 12 }} />
            <div className="skeleton" style={{ height: 14, width: '80%', marginBottom: 8 }} />
            <div className="skeleton" style={{ height: 14, width: '40%' }} />
          </div>
        ))}
      </div>
    );
  }

  return (
    <div>
      {/* Filter Bar */}
      <div className="card" style={{ marginBottom: 24 }}>
        <div className="card-body" style={{ padding: '16px 24px' }}>
          <div style={{ display: 'flex', gap: 12, flexWrap: 'wrap', alignItems: 'center' }}>
            <Filter size={16} color="var(--color-text-secondary)" />
            <select className="form-select" style={{ maxWidth: 200 }} value={typeFilter} onChange={(e) => setTypeFilter(e.target.value)}>
              <option value="">All Types</option>
              {types.map(t => <option key={t} value={t}>{t}</option>)}
            </select>
            <select className="form-select" style={{ maxWidth: 180 }} value={urgencyFilter} onChange={(e) => setUrgencyFilter(e.target.value)}>
              <option value="">All Urgency</option>
              <option value="CRITICAL">🔴 Critical</option>
              <option value="HIGH">🟠 High</option>
              <option value="MEDIUM">🔵 Medium</option>
              <option value="LOW">🟢 Low</option>
            </select>
            <span style={{ fontSize: '0.8rem', color: 'var(--color-text-secondary)' }}>
              {sorted.length} advisories
            </span>
          </div>
        </div>
      </div>

      {/* Advisory Cards */}
      <div style={{ display: 'flex', flexDirection: 'column', gap: 16 }}>
        {sorted.map((adv, i) => {
          const config = typeConfig[adv.advisory_type] || { icon: <BookOpen size={20} />, color: '#757575', bg: '#F5F5F5' };
          const isExpanded = expandedId === adv.advisory_id;
          const urgencyBadge = adv.urgency === 'CRITICAL' ? 'badge-danger' : adv.urgency === 'HIGH' ? 'badge-warning' : adv.urgency === 'MEDIUM' ? 'badge-info' : 'badge-success';

          return (
            <div
              key={adv.advisory_id}
              className="card animate-in"
              style={{
                animationDelay: `${Math.min(i, 6) * 0.05}s`,
                cursor: 'pointer',
                borderLeft: `4px solid ${config.color}`,
              }}
              onClick={() => setExpandedId(isExpanded ? null : adv.advisory_id)}
            >
              <div className="card-body" style={{ padding: 20 }}>
                <div style={{ display: 'flex', alignItems: 'flex-start', gap: 14 }}>
                  <div style={{
                    width: 44, height: 44, borderRadius: 'var(--radius-md)',
                    background: config.bg, color: config.color,
                    display: 'flex', alignItems: 'center', justifyContent: 'center',
                    flexShrink: 0,
                  }}>
                    {config.icon}
                  </div>
                  <div style={{ flex: 1 }}>
                    <div style={{ display: 'flex', alignItems: 'center', gap: 8, flexWrap: 'wrap', marginBottom: 6 }}>
                      <h3 style={{ fontSize: '0.95rem', fontWeight: 700, margin: 0 }}>{adv.title_en}</h3>
                      <span className={`badge ${urgencyBadge}`}>{adv.urgency}</span>
                    </div>
                    <div style={{ display: 'flex', gap: 12, fontSize: '0.75rem', color: 'var(--color-text-secondary)' }}>
                      <span>📋 {adv.advisory_type}</span>
                      <span>🌾 {getCropName(adv.crop_id)}</span>
                      {adv.state_scope && <span>📍 {adv.state_scope}</span>}
                    </div>

                    {isExpanded && (
                      <div style={{
                        marginTop: 14, padding: 16,
                        background: 'var(--color-bg-secondary)',
                        borderRadius: 'var(--radius-md)',
                        fontSize: '0.85rem', lineHeight: 1.7,
                        color: 'var(--color-text-primary)',
                      }}>
                        {adv.content_en}
                      </div>
                    )}

                    {!isExpanded && (
                      <p style={{
                        marginTop: 8, fontSize: '0.8rem',
                        color: 'var(--color-text-secondary)', lineHeight: 1.5,
                        overflow: 'hidden', textOverflow: 'ellipsis',
                        display: '-webkit-box', WebkitLineClamp: 2, WebkitBoxOrient: 'vertical' as const,
                      }}>
                        {adv.content_en}
                      </p>
                    )}
                  </div>
                </div>
              </div>
            </div>
          );
        })}
      </div>
    </div>
  );
}
