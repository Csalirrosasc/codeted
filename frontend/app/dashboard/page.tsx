'use client';

import { useEffect, useState } from 'react';
import { useRouter } from 'next/navigation';
import Link from 'next/link';
import { ShieldCheck, Workflow, Wrench } from 'lucide-react';

import {
  apiClient,
  clearStoredSession,
  getStoredAccessToken,
  getStoredRefreshToken,
} from '@/lib/api-client';
import { type ApiResponse, type DashboardSummary, type UserProfile } from '@/types';

const modules = [
  {
    icon: ShieldCheck,
    title: 'Autenticacion activa',
    description: 'La sesion ya esta respaldada por JWT y refresh token persistido.',
  },
  {
    icon: Workflow,
    title: 'Base para CRM',
    description: 'Desde aqui se puede seguir el salto hacia leads, clientes y proyectos.',
  },
  {
    icon: Wrench,
    title: 'Panel evolutivo',
    description: 'Este dashboard base sirve como punto de entrada para modulos administrativos.',
  },
];

export default function DashboardPage() {
  const router = useRouter();
  const [profile, setProfile] = useState<UserProfile | null>(null);
  const [summary, setSummary] = useState<DashboardSummary | null>(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const accessToken = getStoredAccessToken();

    if (!accessToken) {
      router.replace('/auth/login');
      return;
    }

    async function loadProfile() {
      try {
        const [profileResponse, summaryResponse] = await Promise.all([
          apiClient.get<ApiResponse<UserProfile>>('/auth/me'),
          apiClient.get<ApiResponse<DashboardSummary>>('/dashboard/summary'),
        ]);

        setProfile(profileResponse.data.data);
        setSummary(summaryResponse.data.data);
      } catch {
        clearStoredSession();
        router.replace('/auth/login');
      } finally {
        setLoading(false);
      }
    }

    void loadProfile();
  }, [router]);

  async function handleLogout() {
    const refreshToken = getStoredRefreshToken();

    if (refreshToken) {
      try {
        await apiClient.post('/auth/logout', { refreshToken });
      } catch {
        // Ignore logout API failures and clear the local session anyway.
      }
    }

    clearStoredSession();
    router.push('/auth/login');
  }

  if (loading) {
    return (
      <main className="flex min-h-screen items-center justify-center bg-[#07110f] text-white">
        Cargando panel...
      </main>
    );
  }

  return (
    <main className="min-h-screen bg-[#07110f] px-6 py-10 text-white">
      <div className="mx-auto max-w-6xl space-y-10">
        <header className="flex flex-col gap-5 rounded-[2rem] border border-white/10 bg-white/5 p-8 md:flex-row md:items-end md:justify-between">
          <div className="space-y-3">
            <p className="text-xs font-semibold uppercase tracking-[0.24em] text-[#7fdbc0]">Dashboard</p>
            <h1 className="text-4xl font-semibold">
              Bienvenido, {profile?.username ?? 'usuario'}
            </h1>
            <p className="text-sm leading-7 text-[#a8b8b3]">
              Usuario autenticado con rol <span className="font-semibold text-white">{profile?.role}</span>.
            </p>
          </div>
          <button
            onClick={handleLogout}
            className="rounded-2xl border border-white/10 bg-[#0b1614] px-5 py-3 text-sm transition hover:bg-[#11201d]"
          >
            Cerrar sesion
          </button>
        </header>

        <section className="grid gap-5 md:grid-cols-3">
          {modules.map((module) => (
            <article key={module.title} className="rounded-[1.75rem] border border-white/10 bg-white/5 p-6">
              <module.icon className="size-5 text-[#7fdbc0]" />
              <h2 className="mt-4 text-xl font-semibold">{module.title}</h2>
              <p className="mt-3 text-sm leading-7 text-[#a8b8b3]">{module.description}</p>
            </article>
          ))}
        </section>

        <section className="grid gap-5 md:grid-cols-4">
          {[
            { label: 'Usuarios', value: summary?.totalUsers ?? 0 },
            { label: 'Sesiones activas', value: summary?.activeSessions ?? 0 },
            { label: 'Insights publicados', value: summary?.publishedInsights ?? 0 },
            { label: 'Modulos activos', value: summary?.enabledModules ?? 0 },
          ].map((item) => (
            <article key={item.label} className="rounded-[1.75rem] border border-white/10 bg-[#0b1614] p-6">
              <p className="text-xs uppercase tracking-[0.24em] text-[#7fdbc0]">{item.label}</p>
              <p className="mt-4 text-3xl font-semibold text-white">{item.value}</p>
            </article>
          ))}
        </section>

        <section className="grid gap-5 md:grid-cols-3">
          {[
            { href: '/dashboard/clients', title: 'Clientes', text: 'Registra y sigue leads, clientes activos y oportunidades.' },
            { href: '/dashboard/projects', title: 'Proyectos', text: 'Organiza entregas, estados y fechas clave de ejecución.' },
            { href: '/dashboard/quotes', title: 'Cotizaciones', text: 'Crea propuestas comerciales y controla su avance.' },
            { href: '/dashboard/services', title: 'Servicios', text: 'Administra el catalogo comercial visible en la landing.' },
            { href: '/dashboard/portfolio', title: 'Portafolio', text: 'Publica casos, resultados y activos de confianza comercial.' },
            { href: '/dashboard/blog', title: 'Blog', text: 'Gestiona insights persistidos conectados al frontend publico.' },
            { href: '/dashboard/users', title: 'Usuarios', text: 'Gestiona accesos administrativos y usuarios cliente.' },
            { href: '/dashboard/roles', title: 'Roles', text: 'Revisa el RBAC y la asignacion de permisos por rol.' },
            { href: '/dashboard/leads', title: 'Leads', text: 'Da seguimiento a contactos reales captados desde la landing.' },
            { href: '/client-portal', title: 'Portal cliente', text: 'Consulta la experiencia de acceso para usuarios cliente.' },
          ].map((item) => (
            <Link
              key={item.href}
              href={item.href}
              className="rounded-[1.75rem] border border-white/10 bg-white/5 p-6 transition hover:border-[#2f5a50] hover:bg-[#0c1816]"
            >
              <h2 className="text-2xl font-semibold text-white">{item.title}</h2>
              <p className="mt-3 text-sm leading-7 text-[#a8b8b3]">{item.text}</p>
            </Link>
          ))}
        </section>
      </div>
    </main>
  );
}
