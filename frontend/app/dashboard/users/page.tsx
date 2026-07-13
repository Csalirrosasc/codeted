'use client';

import { FormEvent, useEffect, useState } from 'react';
import Link from 'next/link';
import { useRouter } from 'next/navigation';

import { apiClient, clearStoredSession, getStoredAccessToken } from '@/lib/api-client';
import { type ApiResponse, type Customer, type UserSummary } from '@/types';

const initialForm = {
  username: '',
  fullName: '',
  email: '',
  password: '',
  status: 'active',
  roleNames: ['ROLE_CLIENT'],
  customerPublicId: '',
};

export default function UsersPage() {
  const router = useRouter();
  const [users, setUsers] = useState<UserSummary[]>([]);
  const [customers, setCustomers] = useState<Customer[]>([]);
  const [form, setForm] = useState(initialForm);

  useEffect(() => {
    if (!getStoredAccessToken()) {
      router.replace('/auth/login');
      return;
    }

    async function loadData() {
      try {
        const [usersResponse, customersResponse] = await Promise.all([
          apiClient.get<ApiResponse<UserSummary[]>>('/users'),
          apiClient.get<ApiResponse<Customer[]>>('/customers'),
        ]);
        setUsers(usersResponse.data.data);
        setCustomers(customersResponse.data.data);
      } catch {
        clearStoredSession();
        router.replace('/auth/login');
      }
    }

    void loadData();
  }, [router]);

  async function handleSubmit(event: FormEvent<HTMLFormElement>) {
    event.preventDefault();
    const payload = {
      ...form,
      roleNames: form.roleNames,
      customerPublicId: form.customerPublicId || null,
    };
    const response = await apiClient.post<ApiResponse<UserSummary>>('/users', payload);
    setUsers((current) => [...current, response.data.data]);
    setForm(initialForm);
  }

  return (
    <main className="min-h-screen bg-[#07110f] px-6 py-10 text-white">
      <div className="mx-auto max-w-6xl space-y-8">
        <header className="flex items-end justify-between">
          <div>
            <p className="text-xs font-semibold uppercase tracking-[0.24em] text-[#7fdbc0]">Accesos</p>
            <h1 className="text-4xl font-semibold">Usuarios</h1>
          </div>
          <Link href="/dashboard" className="text-sm text-[#a8b8b3] hover:text-white">Dashboard</Link>
        </header>

        <div className="grid gap-6 lg:grid-cols-[0.95fr_1.05fr]">
          <form onSubmit={handleSubmit} className="space-y-4 rounded-[1.75rem] border border-white/10 bg-white/5 p-6">
            <h2 className="text-xl font-semibold">Crear usuario</h2>
            {[
              ['username', 'Username'],
              ['fullName', 'Nombre completo'],
              ['email', 'Correo'],
              ['password', 'Clave'],
            ].map(([key, label]) => (
              <label key={key} className="block space-y-2">
                <span className="text-sm">{label}</span>
                <input
                  type={key === 'password' ? 'password' : 'text'}
                  value={form[key as keyof typeof form] as string}
                  onChange={(event) => setForm((current) => ({ ...current, [key]: event.target.value }))}
                  className="w-full rounded-2xl border border-white/10 bg-[#0b1614] px-4 py-3 text-sm"
                />
              </label>
            ))}

            <div className="grid gap-4 sm:grid-cols-3">
              <select
                value={form.status}
                onChange={(event) => setForm((current) => ({ ...current, status: event.target.value }))}
                className="rounded-2xl border border-white/10 bg-[#0b1614] px-4 py-3 text-sm"
              >
                <option value="active">active</option>
                <option value="invited">invited</option>
                <option value="disabled">disabled</option>
              </select>
              <select
                value={form.roleNames[0]}
                onChange={(event) => setForm((current) => ({ ...current, roleNames: [event.target.value] }))}
                className="rounded-2xl border border-white/10 bg-[#0b1614] px-4 py-3 text-sm"
              >
                <option value="ROLE_ADMIN">ROLE_ADMIN</option>
                <option value="ROLE_EDITOR">ROLE_EDITOR</option>
                <option value="ROLE_CLIENT">ROLE_CLIENT</option>
              </select>
              <select
                value={form.customerPublicId}
                onChange={(event) => setForm((current) => ({ ...current, customerPublicId: event.target.value }))}
                className="rounded-2xl border border-white/10 bg-[#0b1614] px-4 py-3 text-sm"
              >
                <option value="">Sin cliente asociado</option>
                {customers.map((customer) => (
                  <option key={customer.publicId} value={customer.publicId}>{customer.fullName}</option>
                ))}
              </select>
            </div>

            <button type="submit" className="rounded-2xl bg-[#7fdbc0] px-5 py-3 text-sm font-semibold text-[#07110f]">
              Crear usuario
            </button>
          </form>

          <section className="rounded-[1.75rem] border border-white/10 bg-white/5 p-6">
            <h2 className="text-xl font-semibold">Listado</h2>
            <div className="mt-5 space-y-4">
              {users.map((user) => (
                <article key={user.publicId} className="rounded-2xl border border-white/10 bg-[#0b1614] p-4">
                  <h3 className="text-lg font-semibold">{user.fullName}</h3>
                  <p className="mt-1 text-sm text-[#a8b8b3]">{user.email} · {user.roles.join(', ')}</p>
                </article>
              ))}
            </div>
          </section>
        </div>
      </div>
    </main>
  );
}
