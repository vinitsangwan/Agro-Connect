'use client';

import { useState } from 'react';
import { useRouter } from 'next/navigation';
import { createClient } from '@/utils/supabase/client';
import Link from 'next/link';
import { Wheat, User, Phone, MapPin, Loader2, AlertCircle } from 'lucide-react';

export default function RegisterPage() {
  const [formData, setFormData] = useState({
    firstName: '',
    lastName: '',
    email: '',
    phone: '',
    password: '',
    userType: 'FARMER',
  });
  const [location, setLocation] = useState<{lat: number, lon: number} | null>(null);
  const [locationLoading, setLocationLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const [loading, setLoading] = useState(false);
  const router = useRouter();
  const supabase = createClient();

  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
    const { name, value } = e.target;
    setFormData(prev => ({ ...prev, [name]: value }));
  };

  const getLocation = () => {
    setLocationLoading(true);
    if ('geolocation' in navigator) {
      navigator.geolocation.getCurrentPosition(
        (position) => {
          setLocation({
            lat: position.coords.latitude,
            lon: position.coords.longitude
          });
          setLocationLoading(false);
        },
        (err) => {
          setError('Could not get location. ' + err.message);
          setLocationLoading(false);
        }
      );
    } else {
      setError('Geolocation is not supported by your browser.');
      setLocationLoading(false);
    }
  };

  const handleRegister = async (e: React.FormEvent) => {
    e.preventDefault();
    setLoading(true);
    setError(null);

    const { error: signUpError } = await supabase.auth.signUp({
      email: formData.email,
      password: formData.password,
      options: {
        data: {
          first_name: formData.firstName,
          last_name: formData.lastName,
          phone_number: formData.phone,
          user_type: formData.userType,
          lat: location?.lat?.toString(),
          lon: location?.lon?.toString(),
        }
      }
    });

    if (signUpError) {
      setError(signUpError.message);
      setLoading(false);
    } else {
      // If we don't have email confirmation turned on, it logs them in
      router.push('/dashboard');
      router.refresh();
    }
  };

  return (
    <div className="min-h-screen bg-earth-900 flex flex-col justify-center py-12 sm:px-6 lg:px-8 bg-[url('/harvest-bg.png')] bg-cover bg-blend-overlay bg-black/60">
      <div className="sm:mx-auto sm:w-full sm:max-w-md">
        <div className="flex justify-center">
          <div className="bg-primary-600 p-3 rounded-xl shadow-lg shadow-primary-900/50">
            <Wheat className="h-10 w-10 text-white" />
          </div>
        </div>
        <h2 className="mt-6 text-center text-3xl font-extrabold text-white">
          Create an Account
        </h2>
        <p className="mt-2 text-center text-sm text-earth-300">
          Or{' '}
          <Link href="/login" className="font-medium text-primary-400 hover:text-primary-300">
            sign in to your existing account
          </Link>
        </p>
      </div>

      <div className="mt-8 sm:mx-auto sm:w-full sm:max-w-xl">
        <div className="bg-white/10 backdrop-blur-xl py-8 px-4 shadow sm:rounded-2xl sm:px-10 border border-white/20">
          
          {error && (
            <div className="mb-4 bg-red-500/10 border border-red-500/50 p-4 rounded-lg flex items-start">
              <AlertCircle className="h-5 w-5 text-red-500 mt-0.5 mr-3 flex-shrink-0" />
              <p className="text-sm text-red-100">{error}</p>
            </div>
          )}

          <form className="space-y-6" onSubmit={handleRegister}>
            
            <div className="grid grid-cols-2 gap-4">
              <div>
                <label className="block text-sm font-medium text-earth-100 mb-1">First Name</label>
                <input
                  name="firstName" type="text" required
                  value={formData.firstName} onChange={handleInputChange}
                  className="focus:ring-primary-500 focus:border-primary-500 block w-full sm:text-sm border-earth-600 bg-earth-800/50 text-white rounded-lg py-2.5 px-3"
                  placeholder="Ram"
                />
              </div>
              <div>
                <label className="block text-sm font-medium text-earth-100 mb-1">Last Name</label>
                <input
                  name="lastName" type="text" required
                  value={formData.lastName} onChange={handleInputChange}
                  className="focus:ring-primary-500 focus:border-primary-500 block w-full sm:text-sm border-earth-600 bg-earth-800/50 text-white rounded-lg py-2.5 px-3"
                  placeholder="Kumar"
                />
              </div>
            </div>

            <div>
              <label className="block text-sm font-medium text-earth-100 mb-1">Email Address</label>
              <input
                name="email" type="email" required
                value={formData.email} onChange={handleInputChange}
                className="focus:ring-primary-500 focus:border-primary-500 block w-full sm:text-sm border-earth-600 bg-earth-800/50 text-white rounded-lg py-2.5 px-3"
                placeholder="ram@example.com"
              />
            </div>

            <div>
              <label className="block text-sm font-medium text-earth-100 mb-1">Phone Number</label>
              <div className="relative rounded-md shadow-sm">
                <div className="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
                  <Phone className="h-4 w-4 text-earth-400" />
                </div>
                <input
                  name="phone" type="tel" required
                  value={formData.phone} onChange={handleInputChange}
                  className="focus:ring-primary-500 focus:border-primary-500 block w-full pl-10 sm:text-sm border-earth-600 bg-earth-800/50 text-white rounded-lg py-2.5"
                  placeholder="+91 9876543210"
                />
              </div>
            </div>

            <div>
              <label className="block text-sm font-medium text-earth-100 mb-1">Password</label>
              <input
                name="password" type="password" required minLength={6}
                value={formData.password} onChange={handleInputChange}
                className="focus:ring-primary-500 focus:border-primary-500 block w-full sm:text-sm border-earth-600 bg-earth-800/50 text-white rounded-lg py-2.5 px-3"
                placeholder="••••••••"
              />
            </div>

            <div className="border-t border-earth-700 pt-4">
              <label className="block text-sm font-medium text-earth-100 mb-2">I am registering as a:</label>
              <div className="grid grid-cols-2 gap-4">
                <button
                  type="button"
                  onClick={() => setFormData(prev => ({...prev, userType: 'FARMER'}))}
                  className={`flex items-center justify-center space-x-2 py-3 border rounded-lg transition-colors ${formData.userType === 'FARMER' ? 'bg-primary-600 border-primary-500 text-white' : 'bg-earth-800/50 border-earth-600 text-earth-300 hover:bg-earth-700'}`}
                >
                  <Wheat className="h-5 w-5" />
                  <span>Farmer</span>
                </button>
                <button
                  type="button"
                  onClick={() => setFormData(prev => ({...prev, userType: 'BUYER'}))}
                  className={`flex items-center justify-center space-x-2 py-3 border rounded-lg transition-colors ${formData.userType === 'BUYER' ? 'bg-secondary-600 border-secondary-500 text-white' : 'bg-earth-800/50 border-earth-600 text-earth-300 hover:bg-earth-700'}`}
                >
                  <User className="h-5 w-5" />
                  <span>Buyer</span>
                </button>
              </div>
            </div>

            <div className="border-t border-earth-700 pt-4">
              <label className="block text-sm font-medium text-earth-100 mb-2">Primary Location (Required for localized updates)</label>
              {location ? (
                <div className="bg-earth-800/50 border border-primary-500/50 rounded-lg p-3 flex items-center justify-between">
                  <div className="flex items-center text-sm text-primary-200">
                    <MapPin className="h-4 w-4 mr-2" />
                    Location captured successfully
                  </div>
                  <button type="button" onClick={getLocation} className="text-xs text-primary-400 hover:text-primary-300">
                    Update
                  </button>
                </div>
              ) : (
                <button
                  type="button"
                  onClick={getLocation}
                  disabled={locationLoading}
                  className="w-full flex justify-center py-2.5 px-4 border border-earth-500 border-dashed rounded-lg text-sm font-medium text-earth-200 hover:border-primary-500 hover:text-primary-400 focus:outline-none transition-colors"
                >
                  {locationLoading ? <Loader2 className="h-4 w-4 animate-spin mr-2" /> : <MapPin className="h-4 w-4 mr-2" />}
                  {locationLoading ? 'Getting location...' : 'Get Current Location'}
                </button>
              )}
            </div>

            <div className="pt-2">
              <button
                type="submit"
                disabled={loading || !location}
                className="w-full flex justify-center py-3 px-4 border border-transparent rounded-lg shadow-sm text-sm font-medium text-white bg-primary-600 hover:bg-primary-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-primary-500 disabled:opacity-50 transition-colors"
              >
                {loading ? <Loader2 className="h-5 w-5 animate-spin" /> : 'Create Account'}
              </button>
              {!location && (
                <p className="text-xs text-center text-earth-400 mt-2">Please capture your location to continue.</p>
              )}
            </div>
          </form>

        </div>
      </div>
    </div>
  );
}
