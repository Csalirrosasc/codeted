'use client';

import { FormEvent, useEffect, useState } from 'react';
import Link from 'next/link';
import { useRouter } from 'next/navigation';

import { apiClient, clearStoredSession, getStoredAccessToken } from '@/lib/api-client';
import { type ApiResponse, type Customer } from '@/types';

const initialForm = {
  publicId: '',
  fullName: '',
  email: '',
  phone: '',
  company: '',
  status: 'lead',
  notes: '',
};

export default function ClientsPage() {
  const router = useRouter();
  const [customers, setCustomers] = useState<Customer[]>([]);
  const [form, setForm] = useState(initialForm);
  const [loading, setLoading] = useState(true);
  const [submitting, setSubmitting] = useState(false);
  const isEditing = form.publicId !== '';

  useEffect(() => {
    if (!getStoredAccessToken()) {
      router.replace('/auth/login');
      return;
    }

    async function loadCustomers() {
      try {
        const response = await apiClient.get<ApiResponse<Customer[]>>('/customers');
        setCustomers(response.data.data);
      } catch {
        clearStoredSession();
        router.replace('/auth/login');
      } finally {
        setLoading(false);
      }
    }

    void loadCustomers();
  }, [router]);

  async function handleSubmit(event: FormEvent<HTMLFormElement>) {
    event.preventDefault();
    setSubmitting(true);

    const payload = {
      fullName: form.fullName,
      email: form.email,
      phone: form.phone,
      company: form.company,
      status: form.status,
      notes: form.notes,
    };

    const response = isEditing
      ? await apiClient.patch<ApiResponse<Customer>>(`/customers/${form.publicId}`, payload)
      : await apiClient.post<ApiResponse<Customer>>('/customers', payload);

    setCustomers((current) =>
      isEditing
        ? current.map((customer) => (customer.publicId === response.data.data.publicId ? response.data.data : customer))
        : [...current, response.data.data]
    );
    setForm(initialForm);
    setSubmitting(false);
  }

  async function handleDelete(publicId: string) {
    await apiClient.delete(`/customers/${publicId}`);
    setCustomers((current) => current.filter((customer) => customer.publicId !== publicId));
    if (form.publicId === publicId) {
      setForm(initialForm);
    }
  }

  function handleEdit(customer: Customer) {
    setForm({
      publicId: customer.publicId,
      fullName: customer.fullName,
      email: customer.email ?? '',
      phone: customer.phone ?? '',
      company: customer.company ?? '',
      status: customer.status,
      notes: customer.notes ?? '',
    });
  }

  return (
    <main className="min-h-screen bg-[#07110f] px-6 py-10 text-white">
      <div className="mx-auto max-w-6xl space-y-8">
        <header className="flex flex-col gap-4 md:flex-row md:items-end md:justify-between">
          <div>
            <p className="text-xs font-semibold uppercase tracking-[0.24em] text-[#7fdbc0]">CRM</p>
            <h1 className="text-4xl font-semibold">Clientes</h1>
          </div>
          <div className="flex gap-3 text-sm text-[#a8b8b3]">
            <Link href="/dashboard" className="hover:text-white">Dashboard</Link>
            <Link href="/dashboard/projects" className="hover:text-white">Proyectos</Link>
            <Link href="/dashboard/quotes" className="hover:text-white">Cotizaciones</Link>
          </div>
        </header>

        <div className="grid gap-6 lg:grid-cols-[0.95fr_1.05fr]">
          <form onSubmit={handleSubmit} className="space-y-4 rounded-[1.75rem] border border-white/10 bg-white/5 p-6">
            <h2 className="text-xl font-semibold">{isEditing ? 'Editar cliente' : 'Registrar cliente'}</h2>
            {[
              ['fullName', 'Nombre completo'],
              ['email', 'Correo'],
              ['phone', 'Telefono'],
              ['company', 'Empresa'],
            ].map(([key, label]) => (
              <label key={key} className="block space-y-2">
                <span className="text-sm text-[#d7e2de]">{label}</span>
                <input
                  value={form[key as keyof typeof form]}
                  onChange={(event) =>
                    setForm((current) => ({ ...current, [key]: event.target.value }))
                  }
                  className="w-full rounded-2xl border border-white/10 bg-[#0b1614] px-4 py-3 text-sm outline-none transition focus:border-[#7fdbc0]"
                />
              </label>
            ))}

            <label className="block space-y-2">
              <span className="text-sm text-[#d7e2de]">Estado</span>
              <select
                value={form.status}
                onChange={(event) => setForm((current) => ({ ...current, status: event.target.value }))}
                className="w-full rounded-2xl border border-white/10 bg-[#0b1614] px-4 py-3 text-sm outline-none transition focus:border-[#7fdbc0]"
              >
                <option value="lead">Lead</option>
                <option value="active">Activo</option>
                <option value="won">Ganado</option>
              </select>
            </label>

            <label className="block space-y-2">
              <span className="text-sm text-[#d7e2de]">Notas</span>
              <textarea
                value={form.notes}
                onChange={(event) => setForm((current) => ({ ...current, notes: event.target.value }))}
                className="min-h-28 w-full rounded-2xl border border-white/10 bg-[#0b1614] px-4 py-3 text-sm outline-none transition focus:border-[#7fdbc0]"
              />
            </label>

            <button
              type="submit"
              disabled={submitting}
              className="rounded-2xl bg-[#7fdbc0] px-5 py-3 text-sm font-semibold text-[#07110f] transition hover:bg-[#98e4cd] disabled:opacity-70"
            >
              {submitting ? 'Guardando...' : isEditing ? 'Actualizar cliente' : 'Crear cliente'}
            </button>
            {isEditing ? (
              <button
                type="button"
                onClick={() => setForm(initialForm)}
                className="ml-3 rounded-2xl border border-white/10 px-5 py-3 text-sm"
              >
                Cancelar
              </button>
            ) : null}
          </form>

          <section className="rounded-[1.75rem] border border-white/10 bg-white/5 p-6">
            <h2 className="text-xl font-semibold">Listado</h2>
            <div className="mt-5 space-y-4">
              {loading ? (
                <p className="text-sm text-[#a8b8b3]">Cargando clientes...</p>
              ) : customers.length === 0 ? (
                <p className="text-sm text-[#a8b8b3]">Aun no hay clientes registrados.</p>
              ) : (
                customers.map((customer) => (
                  <article key={customer.publicId} className="rounded-2xl border border-white/10 bg-[#0b1614] p-4">
                    <div className="flex items-start justify-between gap-4">
                      <div>
                        <h3 className="text-lg font-semibold">{customer.fullName}</h3>
                        <p className="mt-1 text-sm text-[#a8b8b3]">
                          {customer.company || 'Sin empresa'} · {customer.email || 'Sin correo'}
                        </p>
                      </div>
                      <div className="flex flex-col items-end gap-2">
                        <span className="rounded-full bg-[#10201d] px-3 py-1 text-xs uppercase tracking-[0.18em] text-[#7fdbc0]">
                          {customer.status}
                        </span>
                        <button onClick={() => handleEdit(customer)} className="text-xs text-[#7fdbc0] hover:text-white">Editar</button>
                        <button onClick={() => handleDelete(customer.publicId)} className="text-xs text-red-300 hover:text-red-200">Eliminar</button>
                      </div>
                    </div>
                  </article>
                ))
              )}
            </div>
          </section>
        </div>
      </div>
    </main>
  );
}
