'use client';

import { FormEvent, useEffect, useState } from 'react';
import Link from 'next/link';
import { useRouter } from 'next/navigation';

import { apiClient, clearStoredSession, getStoredAccessToken } from '@/lib/api-client';
import { type ApiResponse, type BlogPost } from '@/types';

const initialForm = {
  title: '',
  slug: '',
  author: 'CodeTED',
  excerpt: '',
  content: '',
  featured: false,
  published: true,
};

export default function BlogPage() {
  const router = useRouter();
  const [posts, setPosts] = useState<BlogPost[]>([]);
  const [form, setForm] = useState(initialForm);
  const [loading, setLoading] = useState(true);
  const [submitting, setSubmitting] = useState(false);

  useEffect(() => {
    if (!getStoredAccessToken()) {
      router.replace('/auth/login');
      return;
    }

    async function loadPosts() {
      try {
        const response = await apiClient.get<ApiResponse<BlogPost[]>>('/blog/admin/posts');
        setPosts(response.data.data);
      } catch {
        clearStoredSession();
        router.replace('/auth/login');
      } finally {
        setLoading(false);
      }
    }

    void loadPosts();
  }, [router]);

  async function handleSubmit(event: FormEvent<HTMLFormElement>) {
    event.preventDefault();
    setSubmitting(true);

    try {
      const response = await apiClient.post<ApiResponse<BlogPost>>('/blog/posts', form);
      setPosts((current) => [response.data.data, ...current]);
      setForm(initialForm);
    } finally {
      setSubmitting(false);
    }
  }

  async function handleDelete(publicId: string) {
    await apiClient.delete(`/blog/posts/${publicId}`);
    setPosts((current) => current.filter((post) => post.publicId !== publicId));
  }

  return (
    <main className="min-h-screen bg-[#07110f] px-6 py-10 text-white">
      <div className="mx-auto max-w-6xl space-y-8">
        <header className="flex flex-col gap-4 md:flex-row md:items-end md:justify-between">
          <div>
            <p className="text-xs font-semibold uppercase tracking-[0.24em] text-[#7fdbc0]">Contenido</p>
            <h1 className="text-4xl font-semibold">Blog</h1>
          </div>
          <div className="flex gap-3 text-sm text-[#a8b8b3]">
            <Link href="/dashboard" className="hover:text-white">Dashboard</Link>
            <Link href="/dashboard/services" className="hover:text-white">Servicios</Link>
            <Link href="/dashboard/portfolio" className="hover:text-white">Portafolio</Link>
          </div>
        </header>

        <div className="grid gap-6 lg:grid-cols-[0.95fr_1.05fr]">
          <form onSubmit={handleSubmit} className="space-y-4 rounded-[1.75rem] border border-white/10 bg-white/5 p-6">
            <h2 className="text-xl font-semibold">Crear articulo</h2>

            {[
              ['title', 'Titulo'],
              ['slug', 'Slug'],
              ['author', 'Autor'],
              ['excerpt', 'Extracto'],
            ].map(([key, label]) => (
              <label key={key} className="block space-y-2">
                <span className="text-sm text-[#d7e2de]">{label}</span>
                <input
                  value={form[key as keyof typeof form] as string}
                  onChange={(event) => setForm((current) => ({ ...current, [key]: event.target.value }))}
                  className="w-full rounded-2xl border border-white/10 bg-[#0b1614] px-4 py-3 text-sm outline-none transition focus:border-[#7fdbc0]"
                />
              </label>
            ))}

            <label className="block space-y-2">
              <span className="text-sm text-[#d7e2de]">Contenido</span>
              <textarea
                value={form.content}
                onChange={(event) => setForm((current) => ({ ...current, content: event.target.value }))}
                className="min-h-32 w-full rounded-2xl border border-white/10 bg-[#0b1614] px-4 py-3 text-sm outline-none transition focus:border-[#7fdbc0]"
              />
            </label>

            <div className="grid gap-4 sm:grid-cols-2">
              <label className="flex items-center gap-3 rounded-2xl border border-white/10 bg-[#0b1614] px-4 py-3 text-sm">
                <input
                  type="checkbox"
                  checked={form.featured}
                  onChange={(event) => setForm((current) => ({ ...current, featured: event.target.checked }))}
                />
                Destacado
              </label>
              <label className="flex items-center gap-3 rounded-2xl border border-white/10 bg-[#0b1614] px-4 py-3 text-sm">
                <input
                  type="checkbox"
                  checked={form.published}
                  onChange={(event) => setForm((current) => ({ ...current, published: event.target.checked }))}
                />
                Publicado
              </label>
            </div>

            <button
              type="submit"
              disabled={submitting}
              className="rounded-2xl bg-[#7fdbc0] px-5 py-3 text-sm font-semibold text-[#07110f] transition hover:bg-[#98e4cd] disabled:opacity-70"
            >
              {submitting ? 'Guardando...' : 'Crear articulo'}
            </button>
          </form>

          <section className="rounded-[1.75rem] border border-white/10 bg-white/5 p-6">
            <h2 className="text-xl font-semibold">Listado</h2>
            <div className="mt-5 space-y-4">
              {loading ? (
                <p className="text-sm text-[#a8b8b3]">Cargando articulos...</p>
              ) : posts.length === 0 ? (
                <p className="text-sm text-[#a8b8b3]">Aun no hay articulos registrados.</p>
              ) : (
                posts.map((post) => (
                  <article key={post.publicId} className="rounded-2xl border border-white/10 bg-[#0b1614] p-4">
                    <div className="flex items-start justify-between gap-4">
                      <div>
                        <h3 className="text-lg font-semibold">{post.title}</h3>
                        <p className="mt-1 text-sm text-[#a8b8b3]">{post.author} · {post.excerpt}</p>
                      </div>
                      <div className="flex flex-col items-end gap-2">
                        <span className="rounded-full bg-[#10201d] px-3 py-1 text-xs uppercase tracking-[0.18em] text-[#7fdbc0]">
                          {post.published ? 'publicado' : 'borrador'}
                        </span>
                        <button onClick={() => handleDelete(post.publicId)} className="text-xs text-red-300 hover:text-red-200">
                          Eliminar
                        </button>
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
