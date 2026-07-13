'use client';

import { FormEvent, useEffect, useState } from 'react';
import { useRouter } from 'next/navigation';

import { apiClient, clearStoredSession, getStoredAccessToken, storeSession } from '@/lib/api-client';
import { type ApiError, type ApiResponse, type AuthResponse } from '@/types';

export default function LoginPage() {
  const router = useRouter();
  const [username, setUsername] = useState('admin');
  const [password, setPassword] = useState('admin123');
  const [submitting, setSubmitting] = useState(false);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    if (getStoredAccessToken()) {
      router.replace('/dashboard');
    }
  }, [router]);

  async function handleSubmit(event: FormEvent<HTMLFormElement>) {
    event.preventDefault();
    setSubmitting(true);
    setError(null);

    try {
      const response = await apiClient.post<ApiResponse<AuthResponse>>('/auth/login', {
        username,
        password,
      });

      storeSession(response.data.data);
      router.push('/dashboard');
    } catch (requestError) {
      const apiError = requestError as ApiError;
      clearStoredSession();
      setError(apiError.message ?? 'No se pudo iniciar sesion.');
    } finally {
      setSubmitting(false);
    }
  }

  return (
    <main className="flex min-h-screen items-center justify-center bg-[#07110f] px-6 py-16 text-white">
      <div className="w-full max-w-md rounded-[2rem] border border-white/10 bg-white/5 p-8 shadow-2xl backdrop-blur-xl">
        <div className="space-y-3">
          <p className="text-xs font-semibold uppercase tracking-[0.24em] text-[#7fdbc0]">Acceso</p>
          <h1 className="text-3xl font-semibold">Ingresar al panel</h1>
          <p className="text-sm leading-7 text-[#a8b8b3]">
            Accede con un usuario del sistema para validar la autenticacion real del backend.
          </p>
        </div>

        <form className="mt-8 space-y-5" onSubmit={handleSubmit}>
          <label className="block space-y-2">
            <span className="text-sm text-[#d7e2de]">Usuario</span>
            <input
              value={username}
              onChange={(event) => setUsername(event.target.value)}
              className="w-full rounded-2xl border border-white/10 bg-[#0b1614] px-4 py-3 text-sm outline-none transition focus:border-[#7fdbc0]"
              placeholder="admin"
            />
          </label>

          <label className="block space-y-2">
            <span className="text-sm text-[#d7e2de]">Contrasena</span>
            <input
              type="password"
              value={password}
              onChange={(event) => setPassword(event.target.value)}
              className="w-full rounded-2xl border border-white/10 bg-[#0b1614] px-4 py-3 text-sm outline-none transition focus:border-[#7fdbc0]"
              placeholder="••••••••"
            />
          </label>

          {error ? (
            <div className="rounded-2xl border border-red-500/20 bg-red-950/20 px-4 py-3 text-sm text-red-200">
              {error}
            </div>
          ) : null}

          <button
            type="submit"
            disabled={submitting}
            className="w-full rounded-2xl bg-[#7fdbc0] px-4 py-3 text-sm font-semibold text-[#07110f] transition hover:bg-[#98e4cd] disabled:cursor-not-allowed disabled:opacity-70"
          >
            {submitting ? 'Ingresando...' : 'Iniciar sesion'}
          </button>
        </form>
      </div>
    </main>
  );
}
