'use client';

import { useEffect, useState } from 'react';
import Link from 'next/link';
import { useRouter } from 'next/navigation';

import { apiClient, clearStoredSession, getStoredAccessToken } from '@/lib/api-client';
import { type ApiResponse, type ContactLead } from '@/types';

export default function LeadsPage() {
  const router = useRouter();
  const [leads, setLeads] = useState<ContactLead[]>([]);

  useEffect(() => {
    if (!getStoredAccessToken()) {
      router.replace('/auth/login');
      return;
    }

    async function loadLeads() {
      try {
        const response = await apiClient.get<ApiResponse<ContactLead[]>>('/contact/leads');
        setLeads(response.data.data);
      } catch {
        clearStoredSession();
        router.replace('/auth/login');
      }
    }

    void loadLeads();
  }, [router]);

  return (
    <main className="min-h-screen bg-[#07110f] px-6 py-10 text-white">
      <div className="mx-auto max-w-6xl space-y-8">
        <header className="flex items-end justify-between">
          <div>
            <p className="text-xs font-semibold uppercase tracking-[0.24em] text-[#7fdbc0]">Captacion</p>
            <h1 className="text-4xl font-semibold">Leads</h1>
          </div>
          <Link href="/dashboard" className="text-sm text-[#a8b8b3] hover:text-white">Dashboard</Link>
        </header>

        <section className="space-y-4">
          {leads.map((lead) => (
            <article key={lead.publicId} className="rounded-[1.75rem] border border-white/10 bg-white/5 p-6">
              <div className="flex items-start justify-between gap-4">
                <div>
                  <h2 className="text-2xl font-semibold">{lead.fullName}</h2>
                  <p className="mt-2 text-sm text-[#a8b8b3]">{lead.email} · {lead.company || 'Sin empresa'}</p>
                  <p className="mt-3 text-sm text-[#d7e2de]">{lead.message}</p>
                </div>
                <span className="rounded-full bg-[#10201d] px-3 py-1 text-xs text-[#7fdbc0]">{lead.status}</span>
              </div>
            </article>
          ))}
        </section>
      </div>
    </main>
  );
}
