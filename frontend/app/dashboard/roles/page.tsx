'use client';

import { useEffect, useState } from 'react';
import Link from 'next/link';
import { useRouter } from 'next/navigation';

import { apiClient, clearStoredSession, getStoredAccessToken } from '@/lib/api-client';
import { type ApiResponse, type Role } from '@/types';

export default function RolesPage() {
  const router = useRouter();
  const [roles, setRoles] = useState<Role[]>([]);

  useEffect(() => {
    if (!getStoredAccessToken()) {
      router.replace('/auth/login');
      return;
    }

    async function loadRoles() {
      try {
        const response = await apiClient.get<ApiResponse<Role[]>>('/roles');
        setRoles(response.data.data);
      } catch {
        clearStoredSession();
        router.replace('/auth/login');
      }
    }

    void loadRoles();
  }, [router]);

  return (
    <main className="min-h-screen bg-[#07110f] px-6 py-10 text-white">
      <div className="mx-auto max-w-6xl space-y-8">
        <header className="flex items-end justify-between">
          <div>
            <p className="text-xs font-semibold uppercase tracking-[0.24em] text-[#7fdbc0]">RBAC</p>
            <h1 className="text-4xl font-semibold">Roles y permisos</h1>
          </div>
          <Link href="/dashboard" className="text-sm text-[#a8b8b3] hover:text-white">Dashboard</Link>
        </header>

        <section className="grid gap-5 md:grid-cols-2">
          {roles.map((role) => (
            <article key={role.publicId} className="rounded-[1.75rem] border border-white/10 bg-white/5 p-6">
              <h2 className="text-2xl font-semibold">{role.name}</h2>
              <p className="mt-2 text-sm text-[#a8b8b3]">{role.description}</p>
              <div className="mt-4 flex flex-wrap gap-2">
                {role.permissions.map((permission) => (
                  <span key={permission.publicId} className="rounded-full bg-[#10201d] px-3 py-1 text-xs text-[#7fdbc0]">
                    {permission.code}
                  </span>
                ))}
              </div>
            </article>
          ))}
        </section>
      </div>
    </main>
  );
}
