'use client';

import { useState, useEffect } from 'react';
import { createClient } from '@/utils/supabase/client';
import Link from 'next/link';
import { Wheat, Mail, AlertCircle, Loader2, ArrowLeft, CheckCircle } from 'lucide-react';
import AuthCarousel from '@/components/AuthCarousel';

export default function ForgotPasswordPage() {
  const [email, setEmail] = useState('');
  const [error, setError] = useState<string | null>(null);
  const [loading, setLoading] = useState(false);
  const [success, setSuccess] = useState(false);
  const [mounted, setMounted] = useState(false);
  const supabase = createClient();

  useEffect(() => { setMounted(true); }, []);

  const handleResetPassword = async (e: React.FormEvent) => {
    e.preventDefault();
    setLoading(true);
    setError(null);

    try {
      const { error } = await supabase.auth.resetPasswordForEmail(email, {
        redirectTo: `${window.location.origin}/reset-password`,
      });

      if (error) {
        console.error('Supabase reset error:', error);
        setError(`${error.message} (Status: ${error.status || 'unknown'})`);
        setLoading(false);
      } else {
        setSuccess(true);
        setLoading(false);
      }
    } catch (err: any) {
      console.error('Unexpected error:', err);
      setError(err.message || 'An unexpected error occurred.');
      setLoading(false);
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
            <h1 className="auth-title">Reset Password</h1>
            <p className="auth-subtitle">
              Enter your email and we&apos;ll send you a link to reset your password.
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
                Check your email
              </h3>
              <p style={{ fontSize: '0.95rem', color: 'var(--color-text-secondary)', lineHeight: 1.6 }}>
                We&apos;ve sent a password reset link to <strong>{email}</strong>. 
                Please check your inbox and spam folder.
              </p>
              <Link
                href="/login"
                style={{
                  display: 'inline-flex',
                  alignItems: 'center',
                  gap: 8,
                  marginTop: 24,
                  color: 'var(--color-primary)',
                  fontWeight: 600,
                  fontSize: '0.95rem',
                  textDecoration: 'none',
                }}
              >
                <ArrowLeft size={16} />
                Back to Sign In
              </Link>
            </div>
          ) : (
            <form onSubmit={handleResetPassword} className="auth-form">
              <div className="form-group" style={{ marginBottom: 0 }}>
                <label htmlFor="email" className="input-label">Email address</label>
                <div className="input-with-icon">
                  <Mail className="input-icon" size={18} />
                  <input
                    id="email"
                    type="email"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                    required
                    className="modern-input"
                    placeholder="farmer@domain.com"
                  />
                </div>
              </div>

              <button type="submit" disabled={loading} className="btn-solid-primary">
                {loading ? <Loader2 size={20} className="animate-spin" style={{ animation: 'spin 1s linear infinite' }} /> : 'Send Reset Link'}
              </button>
            </form>
          )}

          <div style={{ textAlign: 'center', marginTop: '32px' }}>
            <Link href="/login" style={{ display: 'inline-flex', alignItems: 'center', gap: 8, color: 'var(--color-primary)', fontWeight: 600, fontSize: '0.95rem', textDecoration: 'none' }}>
              <ArrowLeft size={16} />
              Back to Sign In
            </Link>
          </div>

        </div>
      </div>

      {/* Right Side: Image/Branding Section (Hidden on Mobile) */}
      <AuthCarousel />
    </div>
  );
}
