'use client';

import { FormEvent, useEffect, useState } from 'react';
import Link from 'next/link';
import { useRouter } from 'next/navigation';

import { apiClient, clearStoredSession, getStoredAccessToken } from '@/lib/api-client';
import { type ApiResponse, type Customer, type Quote } from '@/types';

const initialForm = {
  publicId: '',
  customerPublicId: '',
  title: '',
  description: '',
  status: 'draft',
  totalAmount: '',
};

export default function QuotesPage() {
  const router = useRouter();
  const [customers, setCustomers] = useState<Customer[]>([]);
  const [quotes, setQuotes] = useState<Quote[]>([]);
  const [form, setForm] = useState(initialForm);
  const [loading, setLoading] = useState(true);
  const [submitting, setSubmitting] = useState(false);
  const isEditing = form.publicId !== '';

  useEffect(() => {
    if (!getStoredAccessToken()) {
      router.replace('/auth/login');
      return;
    }

    async function loadData() {
      try {
        const [customersResponse, quotesResponse] = await Promise.all([
          apiClient.get<ApiResponse<Customer[]>>('/customers'),
          apiClient.get<ApiResponse<Quote[]>>('/quotes'),
        ]);

        setCustomers(customersResponse.data.data);
        setQuotes(quotesResponse.data.data);
      } catch {
        clearStoredSession();
        router.replace('/auth/login');
      } finally {
        setLoading(false);
      }
    }

    void loadData();
  }, [router]);

  async function handleSubmit(event: FormEvent<HTMLFormElement>) {
    event.preventDefault();
    setSubmitting(true);

    const payload = {
      customerPublicId: form.customerPublicId,
      title: form.title,
      description: form.description,
      status: form.status,
      totalAmount: Number(form.totalAmount),
    };

    const response = isEditing
      ? await apiClient.patch<ApiResponse<Quote>>(`/quotes/${form.publicId}`, payload)
      : await apiClient.post<ApiResponse<Quote>>('/quotes', payload);

    setQuotes((current) =>
      isEditing
        ? current.map((quote) => (quote.publicId === response.data.data.publicId ? response.data.data : quote))
        : [...current, response.data.data]
    );
    setForm(initialForm);
    setSubmitting(false);
  }

  async function handleDelete(publicId: string) {
    await apiClient.delete(`/quotes/${publicId}`);
    setQuotes((current) => current.filter((quote) => quote.publicId !== publicId));
    if (form.publicId === publicId) {
      setForm(initialForm);
    }
  }

  function handleEdit(quote: Quote) {
    setForm({
      publicId: quote.publicId,
      customerPublicId: quote.customerPublicId,
      title: quote.title,
      description: quote.description ?? '',
      status: quote.status,
      totalAmount: quote.totalAmount.toString(),
    });
  }

  return (
    <main className="min-h-screen bg-[#07110f] px-6 py-10 text-white">
      <div className="mx-auto max-w-6xl space-y-8">
        <header className="flex flex-col gap-4 md:flex-row md:items-end md:justify-between">
          <div>
            <p className="text-xs font-semibold uppercase tracking-[0.24em] text-[#7fdbc0]">Comercial</p>
            <h1 className="text-4xl font-semibold">Cotizaciones</h1>
          </div>
          <div className="flex gap-3 text-sm text-[#a8b8b3]">
            <Link href="/dashboard" className="hover:text-white">Dashboard</Link>
            <Link href="/dashboard/clients" className="hover:text-white">Clientes</Link>
            <Link href="/dashboard/projects" className="hover:text-white">Proyectos</Link>
          </div>
        </header>

        <div className="grid gap-6 lg:grid-cols-[0.95fr_1.05fr]">
          <form onSubmit={handleSubmit} className="space-y-4 rounded-[1.75rem] border border-white/10 bg-white/5 p-6">
            <h2 className="text-xl font-semibold">{isEditing ? 'Editar cotizacion' : 'Crear cotizacion'}</h2>

            <label className="block space-y-2">
              <span className="text-sm text-[#d7e2de]">Cliente</span>
              <select
                value={form.customerPublicId}
                onChange={(event) => setForm((current) => ({ ...current, customerPublicId: event.target.value }))}
                className="w-full rounded-2xl border border-white/10 bg-[#0b1614] px-4 py-3 text-sm outline-none transition focus:border-[#7fdbc0]"
              >
                <option value="">Selecciona un cliente</option>
                {customers.map((customer) => (
                  <option key={customer.publicId} value={customer.publicId}>
                    {customer.fullName}
                  </option>
                ))}
              </select>
            </label>

            <label className="block space-y-2">
              <span className="text-sm text-[#d7e2de]">Titulo</span>
              <input
                value={form.title}
                onChange={(event) => setForm((current) => ({ ...current, title: event.target.value }))}
                className="w-full rounded-2xl border border-white/10 bg-[#0b1614] px-4 py-3 text-sm outline-none transition focus:border-[#7fdbc0]"
              />
            </label>

            <label className="block space-y-2">
              <span className="text-sm text-[#d7e2de]">Descripcion</span>
              <textarea
                value={form.description}
                onChange={(event) => setForm((current) => ({ ...current, description: event.target.value }))}
                className="min-h-24 w-full rounded-2xl border border-white/10 bg-[#0b1614] px-4 py-3 text-sm outline-none transition focus:border-[#7fdbc0]"
              />
            </label>

            <div className="grid gap-4 sm:grid-cols-2">
              <label className="block space-y-2">
                <span className="text-sm text-[#d7e2de]">Estado</span>
                <select
                  value={form.status}
                  onChange={(event) => setForm((current) => ({ ...current, status: event.target.value }))}
                  className="w-full rounded-2xl border border-white/10 bg-[#0b1614] px-4 py-3 text-sm outline-none transition focus:border-[#7fdbc0]"
                >
                  <option value="draft">Draft</option>
                  <option value="sent">Sent</option>
                  <option value="approved">Approved</option>
                </select>
              </label>
              <label className="block space-y-2">
                <span className="text-sm text-[#d7e2de]">Monto</span>
                <input
                  type="number"
                  min="0"
                  step="0.01"
                  value={form.totalAmount}
                  onChange={(event) => setForm((current) => ({ ...current, totalAmount: event.target.value }))}
                  className="w-full rounded-2xl border border-white/10 bg-[#0b1614] px-4 py-3 text-sm outline-none transition focus:border-[#7fdbc0]"
                />
              </label>
            </div>

            <button
              type="submit"
              disabled={submitting}
              className="rounded-2xl bg-[#7fdbc0] px-5 py-3 text-sm font-semibold text-[#07110f] transition hover:bg-[#98e4cd] disabled:opacity-70"
            >
              {submitting ? 'Guardando...' : isEditing ? 'Actualizar cotizacion' : 'Crear cotizacion'}
            </button>
            {isEditing ? (
              <button type="button" onClick={() => setForm(initialForm)} className="ml-3 rounded-2xl border border-white/10 px-5 py-3 text-sm">
                Cancelar
              </button>
            ) : null}
          </form>

          <section className="rounded-[1.75rem] border border-white/10 bg-white/5 p-6">
            <h2 className="text-xl font-semibold">Listado</h2>
            <div className="mt-5 space-y-4">
              {loading ? (
                <p className="text-sm text-[#a8b8b3]">Cargando cotizaciones...</p>
              ) : quotes.length === 0 ? (
                <p className="text-sm text-[#a8b8b3]">Aun no hay cotizaciones registradas.</p>
              ) : (
                quotes.map((quote) => (
                  <article key={quote.publicId} className="rounded-2xl border border-white/10 bg-[#0b1614] p-4">
                    <div className="flex items-start justify-between gap-4">
                      <div>
                        <h3 className="text-lg font-semibold">{quote.title}</h3>
                        <p className="mt-1 text-sm text-[#a8b8b3]">
                          {quote.customerName} · S/ {quote.totalAmount.toFixed(2)}
                        </p>
                      </div>
                      <div className="flex flex-col items-end gap-2">
                        <span className="rounded-full bg-[#10201d] px-3 py-1 text-xs uppercase tracking-[0.18em] text-[#7fdbc0]">
                          {quote.status}
                        </span>
                        <button onClick={() => handleEdit(quote)} className="text-xs text-[#7fdbc0] hover:text-white">Editar</button>
                        <button onClick={() => handleDelete(quote.publicId)} className="text-xs text-red-300 hover:text-red-200">Eliminar</button>
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
