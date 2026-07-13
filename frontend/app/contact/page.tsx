'use client';

import { FormEvent, useState } from 'react';
import Link from 'next/link';

import { apiClient } from '@/lib/api-client';
import { type ApiResponse, type ContactLead } from '@/types';

const initialForm = {
  fullName: '',
  email: '',
  phone: '',
  company: '',
  message: '',
  source: 'website',
};

export default function ContactPage() {
  const [form, setForm] = useState(initialForm);
  const [submitted, setSubmitted] = useState(false);
  const [submitting, setSubmitting] = useState(false);

  async function handleSubmit(event: FormEvent<HTMLFormElement>) {
    event.preventDefault();
    setSubmitting(true);

    try {
      await apiClient.post<ApiResponse<ContactLead>>('/contact/requests', form);
      setSubmitted(true);
      setForm(initialForm);
    } finally {
      setSubmitting(false);
    }
  }

  return (
    <main className="min-h-screen bg-[#07110f] px-6 py-10 text-white">
      <div className="mx-auto max-w-4xl space-y-8">
        <header className="space-y-3">
          <Link href="/" className="text-sm text-[#7fdbc0] hover:text-white">Volver al inicio</Link>
          <p className="text-xs font-semibold uppercase tracking-[0.24em] text-[#7fdbc0]">Contacto</p>
          <h1 className="text-4xl font-semibold">Cuentanos que necesita tu negocio</h1>
          <p className="max-w-2xl text-sm leading-7 text-[#a8b8b3]">
            Esta solicitud entra al modulo real de leads del sistema para que el equipo pueda responder y dar seguimiento.
          </p>
        </header>

        <form onSubmit={handleSubmit} className="space-y-4 rounded-[2rem] border border-white/10 bg-white/5 p-8">
          {[
            ['fullName', 'Nombre completo'],
            ['email', 'Correo'],
            ['phone', 'Telefono'],
            ['company', 'Empresa'],
          ].map(([key, label]) => (
            <label key={key} className="block space-y-2">
              <span className="text-sm">{label}</span>
              <input
                value={form[key as keyof typeof form] as string}
                onChange={(event) => setForm((current) => ({ ...current, [key]: event.target.value }))}
                className="w-full rounded-2xl border border-white/10 bg-[#0b1614] px-4 py-3 text-sm"
              />
            </label>
          ))}

          <label className="block space-y-2">
            <span className="text-sm">Mensaje</span>
            <textarea
              value={form.message}
              onChange={(event) => setForm((current) => ({ ...current, message: event.target.value }))}
              className="min-h-36 w-full rounded-2xl border border-white/10 bg-[#0b1614] px-4 py-3 text-sm"
            />
          </label>

          <button
            type="submit"
            disabled={submitting}
            className="rounded-2xl bg-[#7fdbc0] px-5 py-3 text-sm font-semibold text-[#07110f] disabled:opacity-70"
          >
            {submitting ? 'Enviando...' : 'Enviar solicitud'}
          </button>

          {submitted ? (
            <p className="text-sm text-[#7fdbc0]">Solicitud enviada correctamente.</p>
          ) : null}
        </form>
      </div>
    </main>
  );
}
