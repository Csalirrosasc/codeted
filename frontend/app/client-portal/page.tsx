'use client';

import { useEffect, useState } from 'react';
import Link from 'next/link';
import { useRouter } from 'next/navigation';

import { apiClient, clearStoredSession, getStoredAccessToken } from '@/lib/api-client';
import { type ApiResponse, type ClientPortal } from '@/types';

export default function ClientPortalPage() {
  const router = useRouter();
  const [portal, setPortal] = useState<ClientPortal | null>(null);

  useEffect(() => {
    if (!getStoredAccessToken()) {
      router.replace('/auth/login');
      return;
    }

    async function loadPortal() {
      try {
        const response = await apiClient.get<ApiResponse<ClientPortal>>('/client-portal/me');
        setPortal(response.data.data);
      } catch {
        clearStoredSession();
        router.replace('/auth/login');
      }
    }

    void loadPortal();
  }, [router]);

  return (
    <main className="min-h-screen bg-[#07110f] px-6 py-10 text-white">
      <div className="mx-auto max-w-6xl space-y-8">
        <header className="flex items-end justify-between">
          <div>
            <p className="text-xs font-semibold uppercase tracking-[0.24em] text-[#7fdbc0]">Portal cliente</p>
            <h1 className="text-4xl font-semibold">{portal?.customerName || 'Mi cuenta'}</h1>
          </div>
          <Link href="/dashboard" className="text-sm text-[#a8b8b3] hover:text-white">Volver</Link>
        </header>

        <section className="grid gap-6 md:grid-cols-2">
          <article className="rounded-[1.75rem] border border-white/10 bg-white/5 p-6">
            <h2 className="text-2xl font-semibold">Proyectos</h2>
            <div className="mt-4 space-y-3">
              {portal?.projects.map((project) => (
                <div key={project.publicId} className="rounded-2xl border border-white/10 bg-[#0b1614] p-4">
                  <p className="font-semibold">{project.name}</p>
                  <p className="mt-1 text-sm text-[#a8b8b3]">{project.status}</p>
                </div>
              ))}
            </div>
          </article>

          <article className="rounded-[1.75rem] border border-white/10 bg-white/5 p-6">
            <h2 className="text-2xl font-semibold">Cotizaciones</h2>
            <div className="mt-4 space-y-3">
              {portal?.quotes.map((quote) => (
                <div key={quote.publicId} className="rounded-2xl border border-white/10 bg-[#0b1614] p-4">
                  <p className="font-semibold">{quote.title}</p>
                  <p className="mt-1 text-sm text-[#a8b8b3]">{quote.status} · S/ {quote.totalAmount.toFixed(2)}</p>
                </div>
              ))}
            </div>
          </article>
        </section>
      </div>
    </main>
  );
}
