'use client';

import { useState, useEffect } from 'react';
import { useRouter } from 'next/navigation';
import { createClient } from '@/utils/supabase/client';
import Link from 'next/link';
import { Wheat, Lock, AlertCircle, Loader2, CheckCircle } from 'lucide-react';
import AuthCarousel from '@/components/AuthCarousel';

export default function ResetPasswordPage() {
  const [password, setPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const [error, setError] = useState<string | null>(null);
  const [loading, setLoading] = useState(false);
  const [success, setSuccess] = useState(false);
  const [mounted, setMounted] = useState(false);
  const router = useRouter();
  const supabase = createClient();

  useEffect(() => { setMounted(true); }, []);

  const handleUpdatePassword = async (e: React.FormEvent) => {
    e.preventDefault();
    setError(null);

    if (password !== confirmPassword) {
      setError('Passwords do not match.');
      return;
    }

    if (password.length < 6) {
      setError('Password must be at least 6 characters.');
      return;
    }

    setLoading(true);

    const { error } = await supabase.auth.updateUser({
      password,
    });

    if (error) {
      setError(error.message);
      setLoading(false);
    } else {
      setSuccess(true);
      setLoading(false);
      // Redirect to dashboard after 3 seconds
      setTimeout(() => {
        router.push('/dashboard');
      }, 3000);
    }
  };

  if (!mounted) return null;

  return (
    <div className="auth-split-layout">
      {/* Left Side: Form Section */}
      <div className="auth-form-section">
        <div className="auth-form-container">

          <div className="auth-header">
            <div className="auth-logo">
              <div className="auth-logo-icon"><Wheat size={24} /></div>
              <span className="auth-logo-text">Agro-Connect</span>
            </div>
            <h1 className="auth-title">Set New Password</h1>
            <p className="auth-subtitle">
              Enter your new password below.
            </p>
          </div>

          {error && (
            <div className="auth-error-alert">
              <AlertCircle size={20} />
              <span>{error}</span>
            </div>
          )}

          {success ? (
            <div style={{
              background: 'var(--color-primary-50)',
              border: '1px solid var(--color-primary-100)',
              borderRadius: 'var(--radius-md)',
              padding: '24px',
              textAlign: 'center',
            }}>
              <CheckCircle size={48} style={{ color: 'var(--color-primary)', marginBottom: 16 }} />
              <h3 style={{ fontSize: '1.15rem', fontWeight: 700, marginBottom: 8, color: 'var(--color-text-primary)' }}>
                Password Updated!
              </h3>
              <p style={{ fontSize: '0.95rem', color: 'var(--color-text-secondary)', lineHeight: 1.6 }}>
                Your password has been successfully updated. Redirecting to dashboard...
              </p>
            </div>
          ) : (
            <form onSubmit={handleUpdatePassword} className="auth-form">
              <div className="form-group" style={{ marginBottom: 0 }}>
                <label htmlFor="password" className="input-label">New Password</label>
                <div className="input-with-icon">
                  <Lock className="input-icon" size={18} />
                  <input
                    id="password"
                    type="password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    required
                    minLength={6}
                    className="modern-input"
                    placeholder="••••••••"
                  />
                </div>
              </div>

              <div className="form-group" style={{ marginBottom: 0 }}>
                <label htmlFor="confirmPassword" className="input-label">Confirm New Password</label>
                <div className="input-with-icon">
                  <Lock className="input-icon" size={18} />
                  <input
                    id="confirmPassword"
                    type="password"
                    value={confirmPassword}
                    onChange={(e) => setConfirmPassword(e.target.value)}
                    required
                    minLength={6}
                    className="modern-input"
                    placeholder="••••••••"
                  />
                </div>
              </div>

              <button type="submit" disabled={loading} className="btn-solid-primary">
                {loading ? <Loader2 size={20} className="animate-spin" style={{ animation: 'spin 1s linear infinite' }} /> : 'Update Password'}
              </button>
            </form>
          )}

        </div>
      </div>

      {/* Right Side: Image/Branding Section (Hidden on Mobile) */}
      <AuthCarousel />
    </div>
  );
}
