'use client';

import Link from 'next/link';
import { useQuery } from '@tanstack/react-query';
import { motion } from 'framer-motion';
import {
  Bot,
  BrainCircuit,
  BriefcaseBusiness,
  ChevronRight,
  Gauge,
  Globe,
  LayoutTemplate,
  MessageSquareQuote,
  Rocket,
  ShieldCheck,
  Sparkles,
  Workflow,
} from 'lucide-react';

import { Button } from '@/components/ui/button';
import { apiClient } from '@/lib/api-client';
import { type ApiResponse } from '@/types';

interface BlogPost {
  id: string;
  title: string;
  author: string;
  excerpt: string;
}

const services = [
  {
    icon: LayoutTemplate,
    title: 'Landing Pages Premium',
    description: 'Paginas comerciales pensadas para convertir visitas en reuniones, leads y ventas.',
  },
  {
    icon: Workflow,
    title: 'Automatizacion de procesos',
    description: 'Reducimos trabajo manual con flujos conectados a formularios, correo y WhatsApp.',
  },
  {
    icon: BriefcaseBusiness,
    title: 'Sistemas internos',
    description: 'CRM, paneles y herramientas a medida para ordenar la operacion del negocio.',
  },
  {
    icon: BrainCircuit,
    title: 'IA aplicada',
    description: 'Usamos IA para acelerar atencion, clasificacion y seguimiento comercial.',
  },
];

const pillars = [
  'Arquitectura empresarial desde el dia 1',
  'Diseno moderno enfocado en conversion',
  'Backend profesional con Spring Boot',
  'Escalabilidad para evolucionar a SaaS',
];

const highlights = [
  { label: 'Tiempo de respuesta', value: '< 24h' },
  { label: 'Modelo objetivo', value: 'Monolito modular' },
  { label: 'Base tecnologica', value: 'Next.js + Spring Boot' },
  { label: 'Enfoque comercial', value: 'Captacion + operacion' },
];

const processSteps = [
  'Diagnostico del negocio y definicion de la propuesta.',
  'Diseno de una experiencia clara para captar clientes.',
  'Implementacion full-stack sobre arquitectura mantenible.',
  'Medicion, iteracion y crecimiento hacia automatizacion o SaaS.',
];

const faqs = [
  {
    question: 'CodeTED vende paginas web o software completo?',
    answer:
      'Ambas. La puerta de entrada es una presencia comercial fuerte, pero la vision del producto incluye CRM, panel administrativo y portal del cliente.',
  },
  {
    question: 'Puedo empezar con una landing y escalar despues?',
    answer:
      'Si. La arquitectura esta pensada para crecer sin rehacer todo el proyecto cuando llegue el momento de anadir modulos internos.',
  },
  {
    question: 'Se puede integrar con WhatsApp, formularios y automatizaciones?',
    answer:
      'Si. Ese es uno de los focos del proyecto: convertir la captacion en un flujo comercial ordenado y medible.',
  },
];

const testimonials = [
  {
    name: 'Operaciones PYME',
    quote: 'Pasamos de explicar el servicio por WhatsApp todo el dia a tener una presencia mucho mas clara y profesional.',
  },
  {
    name: 'Equipo Comercial',
    quote: 'La combinacion de landing, seguimiento y panel interno reduce friccion y acelera la respuesta al prospecto.',
  },
  {
    name: 'Direccion General',
    quote: 'Lo importante no es solo verse bien, sino construir una base digital lista para crecer a procesos internos.',
  },
];

function SectionTitle({
  eyebrow,
  title,
  description,
}: {
  eyebrow: string;
  title: string;
  description: string;
}) {
  return (
    <div className="max-w-2xl space-y-4">
      <span className="inline-flex rounded-full border border-[#1f3c36] bg-[#0d1b19] px-3 py-1 text-xs font-semibold uppercase tracking-[0.24em] text-[#7fdbc0]">
        {eyebrow}
      </span>
      <h2 className="text-3xl font-semibold tracking-tight text-white sm:text-4xl">{title}</h2>
      <p className="text-sm leading-7 text-[#a7b6b2] sm:text-base">{description}</p>
    </div>
  );
}

export default function HomePage() {
  const { data: posts, isLoading, error, refetch, isFetching } = useQuery<BlogPost[]>({
    queryKey: ['blog-posts'],
    queryFn: async () => {
      const response = await apiClient.get<ApiResponse<BlogPost[]>>('/blog/posts');
      return response.data.data;
    },
  });

  return (
    <div className="min-h-screen bg-[#07110f] text-white selection:bg-[#7fdbc0] selection:text-[#07110f]">
      <div className="pointer-events-none fixed inset-0 opacity-80">
        <div className="absolute inset-0 bg-[radial-gradient(circle_at_top_left,rgba(98,208,173,0.18),transparent_35%),radial-gradient(circle_at_top_right,rgba(255,181,71,0.12),transparent_28%),linear-gradient(180deg,#07110f_0%,#081513_100%)]" />
        <div className="absolute inset-0 bg-[linear-gradient(rgba(255,255,255,0.03)_1px,transparent_1px),linear-gradient(90deg,rgba(255,255,255,0.03)_1px,transparent_1px)] bg-[size:44px_44px]" />
      </div>

      <div className="relative mx-auto max-w-7xl px-6 pb-20 pt-6 sm:px-8 lg:px-10">
        <header className="sticky top-4 z-20 mb-12 rounded-full border border-white/10 bg-[#0d1715]/80 px-4 py-3 backdrop-blur-xl">
          <div className="flex items-center justify-between gap-4">
            <div>
              <p className="text-sm font-semibold tracking-[0.28em] text-[#7fdbc0]">CODETED</p>
              <p className="text-xs text-[#8b9a96]">Software que impulsa negocios reales</p>
            </div>
            <nav className="hidden items-center gap-6 text-sm text-[#c8d5d1] md:flex">
              <a href="#servicios" className="transition hover:text-white">Servicios</a>
              <a href="#proceso" className="transition hover:text-white">Proceso</a>
              <a href="#prueba-social" className="transition hover:text-white">Prueba social</a>
              <a href="#contacto" className="transition hover:text-white">Contacto</a>
            </nav>
            <Link
              href="/contact"
              className="inline-flex items-center justify-center rounded-lg bg-[#7fdbc0] px-5 py-2 text-sm font-medium text-[#07110f] transition hover:bg-[#98e4cd]"
            >
              Solicitar propuesta
            </Link>
          </div>
        </header>

        <main className="space-y-24">
          <section className="grid gap-14 lg:grid-cols-[1.1fr_0.9fr] lg:items-end">
            <motion.div
              initial={{ opacity: 0, y: 24 }}
              animate={{ opacity: 1, y: 0 }}
              transition={{ duration: 0.6 }}
              className="space-y-8"
            >
              <span className="inline-flex items-center gap-2 rounded-full border border-[#2c4942] bg-[#0e1c19] px-4 py-2 text-xs font-semibold uppercase tracking-[0.2em] text-[#7fdbc0]">
                <Sparkles className="size-3.5" />
                Landing premium + CRM + automatizacion
              </span>
              <div className="space-y-6">
                <h1 className="max-w-4xl text-5xl font-semibold tracking-tight text-white sm:text-6xl lg:text-7xl">
                  Disenamos sistemas y experiencias que convierten visitas en clientes.
                </h1>
                <p className="max-w-2xl text-base leading-8 text-[#afbeb9] sm:text-lg">
                  CodeTED une diseno premium, arquitectura empresarial y automatizacion
                  inteligente para ayudar a pequenas y medianas empresas a vender mejor,
                  responder mas rapido y operar con mas orden.
                </p>
              </div>
              <div className="flex flex-col gap-4 sm:flex-row">
                <Link
                  href="/contact"
                  className="inline-flex h-11 items-center justify-center rounded-lg bg-[#7fdbc0] px-6 text-sm font-medium text-[#07110f] transition hover:bg-[#98e4cd]"
                >
                  Solicitar cotizacion
                </Link>
                <a
                  href="#contacto"
                  className="inline-flex h-11 items-center justify-center rounded-lg border border-white/15 bg-white/5 px-6 text-sm font-medium text-white transition hover:bg-white/10"
                >
                  Agendar reunion
                </a>
              </div>
              <div className="grid gap-3 sm:grid-cols-2">
                {pillars.map((pillar) => (
                  <div
                    key={pillar}
                    className="flex items-center gap-3 rounded-2xl border border-white/8 bg-white/4 px-4 py-4 text-sm text-[#dbe5e1]"
                  >
                    <ShieldCheck className="size-4 text-[#7fdbc0]" />
                    <span>{pillar}</span>
                  </div>
                ))}
              </div>
            </motion.div>

            <motion.aside
              initial={{ opacity: 0, scale: 0.96 }}
              animate={{ opacity: 1, scale: 1 }}
              transition={{ delay: 0.15, duration: 0.55 }}
              className="rounded-[2rem] border border-white/10 bg-[linear-gradient(160deg,rgba(14,28,25,0.95),rgba(10,21,19,0.88))] p-6 shadow-[0_30px_80px_rgba(0,0,0,0.35)]"
            >
              <div className="flex items-center justify-between border-b border-white/10 pb-5">
                <div>
                  <p className="text-sm font-semibold text-white">Stack de confianza</p>
                  <p className="text-xs text-[#8fa19c]">Preparado para crecer a producto SaaS</p>
                </div>
                <Rocket className="size-5 text-[#ffb547]" />
              </div>
              <div className="mt-6 grid gap-4 sm:grid-cols-2">
                {highlights.map((item) => (
                  <div key={item.label} className="rounded-2xl border border-white/8 bg-black/20 p-4">
                    <p className="text-xs uppercase tracking-[0.2em] text-[#7fdbc0]">{item.label}</p>
                    <p className="mt-3 text-lg font-semibold text-white">{item.value}</p>
                  </div>
                ))}
              </div>
              <div className="mt-6 rounded-3xl border border-[#2b4943] bg-[#0e1e1b] p-5">
                <div className="flex items-start gap-3">
                  <Gauge className="mt-1 size-5 text-[#7fdbc0]" />
                  <div className="space-y-2">
                    <p className="text-sm font-semibold text-white">Objetivo inmediato</p>
                    <p className="text-sm leading-7 text-[#afbeb9]">
                      Entregar una presencia comercial solida hoy, sin cerrar el camino
                      hacia CRM, panel administrativo, portal cliente y automatizaciones
                      futuras.
                    </p>
                  </div>
                </div>
              </div>
            </motion.aside>
          </section>

          <section className="grid gap-6 rounded-[2rem] border border-white/8 bg-white/4 p-6 lg:grid-cols-4">
            {[
              { icon: Globe, title: 'Presencia comercial', text: 'Diseno web orientado a captar clientes y transmitir autoridad.' },
              { icon: Bot, title: 'Automatizacion', text: 'Flujos que reducen tareas repetitivas y mejoran la atencion.' },
              { icon: MessageSquareQuote, title: 'Seguimiento comercial', text: 'Leads, cotizaciones y conversaciones organizadas.' },
              { icon: BriefcaseBusiness, title: 'Escalabilidad', text: 'La misma base tecnica puede evolucionar a CRM y portal.' },
            ].map((item) => (
              <div key={item.title} className="rounded-2xl border border-white/8 bg-[#0b1614] p-5">
                <item.icon className="size-5 text-[#7fdbc0]" />
                <h3 className="mt-4 text-lg font-semibold text-white">{item.title}</h3>
                <p className="mt-2 text-sm leading-7 text-[#9eb0aa]">{item.text}</p>
              </div>
            ))}
          </section>

          <section id="servicios" className="space-y-10">
            <SectionTitle
              eyebrow="Servicios"
              title="Construimos activos digitales que generan resultados, no solo pantallas bonitas."
              description="Cada servicio se disena para resolver un cuello de botella real del negocio: captar prospectos, responder mas rapido, ordenar la operacion o preparar el crecimiento a un modelo SaaS."
            />
            <div className="grid gap-5 lg:grid-cols-2">
              {services.map((service, index) => (
                <motion.article
                  key={service.title}
                  initial={{ opacity: 0, y: 24 }}
                  whileInView={{ opacity: 1, y: 0 }}
                  viewport={{ once: true, amount: 0.2 }}
                  transition={{ delay: index * 0.08, duration: 0.45 }}
                  className="rounded-[1.75rem] border border-white/8 bg-[linear-gradient(180deg,rgba(255,255,255,0.05),rgba(255,255,255,0.02))] p-6"
                >
                  <div className="flex items-start justify-between gap-4">
                    <div className="space-y-3">
                      <div className="inline-flex rounded-2xl border border-[#26443d] bg-[#0e1d1a] p-3">
                        <service.icon className="size-5 text-[#7fdbc0]" />
                      </div>
                      <h3 className="text-2xl font-semibold text-white">{service.title}</h3>
                    </div>
                    <ChevronRight className="size-5 text-[#ffb547]" />
                  </div>
                  <p className="mt-4 max-w-xl text-sm leading-7 text-[#aab8b4]">{service.description}</p>
                </motion.article>
              ))}
            </div>
          </section>

          <section id="proceso" className="grid gap-10 lg:grid-cols-[0.9fr_1.1fr]">
            <SectionTitle
              eyebrow="Proceso"
              title="Una ruta clara para convertir una necesidad comercial en una plataforma sostenible."
              description="No trabajamos a punta de tareas sueltas. Priorizamos arquitectura, conversion y mantenibilidad para que cada entrega sirva tambien a la siguiente fase del negocio."
            />
            <div className="space-y-4">
              {processSteps.map((step, index) => (
                <div key={step} className="flex gap-4 rounded-3xl border border-white/8 bg-white/4 p-5">
                  <div className="flex size-10 shrink-0 items-center justify-center rounded-full bg-[#7fdbc0] font-semibold text-[#07110f]">
                    {index + 1}
                  </div>
                  <p className="pt-1 text-sm leading-7 text-[#d4dfdb]">{step}</p>
                </div>
              ))}
            </div>
          </section>

          <section id="prueba-social" className="space-y-10">
            <SectionTitle
              eyebrow="Prueba Social"
              title="Una presencia comercial fuerte funciona mejor cuando tambien ordena la respuesta y el seguimiento."
              description="La meta de CodeTED no es publicar una web bonita y desaparecer. Buscamos una base comercial que conecte captacion, operacion y crecimiento."
            />
            <div className="grid gap-5 lg:grid-cols-3">
              {testimonials.map((testimonial) => (
                <article key={testimonial.name} className="rounded-[1.75rem] border border-white/8 bg-white/4 p-6">
                  <p className="text-sm leading-7 text-[#d8e1de]">&ldquo;{testimonial.quote}&rdquo;</p>
                  <p className="mt-5 text-xs font-semibold uppercase tracking-[0.22em] text-[#7fdbc0]">{testimonial.name}</p>
                </article>
              ))}
            </div>
          </section>

          <section id="insights" className="space-y-10">
            <div className="flex flex-col gap-6 lg:flex-row lg:items-end lg:justify-between">
              <SectionTitle
                eyebrow="Insights"
                title="Contenido tecnico que tambien demuestra capacidad de ejecucion."
                description="La landing no solo vende; tambien educa y construye confianza. Por eso conectamos la homepage con el backend y dejamos una base lista para blog y SEO."
              />
              <Button
                variant="outline"
                onClick={() => refetch()}
                disabled={isFetching}
                className="border-white/12 bg-white/5 text-white hover:bg-white/10"
              >
                {isFetching ? 'Actualizando...' : 'Actualizar articulos'}
              </Button>
            </div>

            {isLoading ? (
              <div className="grid gap-5 md:grid-cols-2">
                {[1, 2].map((item) => (
                  <div
                    key={item}
                    className="h-56 animate-pulse rounded-[1.75rem] border border-white/8 bg-white/4"
                  />
                ))}
              </div>
            ) : error ? (
              <div className="rounded-[1.75rem] border border-red-500/20 bg-red-950/20 p-6 text-sm leading-7 text-red-200">
                No se pudieron obtener los articulos del backend. La landing sigue funcionando, pero la
                integracion de contenidos requiere que la API este activa en `http://localhost:8080/api`.
              </div>
            ) : (
              <div className="grid gap-5 md:grid-cols-2">
                {posts?.map((post) => (
                  <article
                    key={post.id}
                    className="rounded-[1.75rem] border border-white/8 bg-[#0b1614] p-6 transition hover:border-[#2e5a50] hover:bg-[#0d1a18]"
                  >
                    <p className="text-xs font-semibold uppercase tracking-[0.2em] text-[#7fdbc0]">{post.author}</p>
                    <h3 className="mt-3 text-2xl font-semibold text-white">{post.title}</h3>
                    <p className="mt-4 text-sm leading-7 text-[#aab8b4]">{post.excerpt}</p>
                  </article>
                ))}
              </div>
            )}
          </section>

          <section className="grid gap-12 lg:grid-cols-[0.95fr_1.05fr]">
            <div className="space-y-8">
              <SectionTitle
                eyebrow="FAQ"
                title="Preguntas tipicas antes de empezar un proyecto digital serio."
                description="La meta es reducir friccion, aclarar expectativas y mostrar que detras del diseno hay un plan tecnico de crecimiento."
              />
              <div className="space-y-4">
                {faqs.map((faq) => (
                  <div key={faq.question} className="rounded-3xl border border-white/8 bg-white/4 p-5">
                    <h3 className="text-lg font-semibold text-white">{faq.question}</h3>
                    <p className="mt-3 text-sm leading-7 text-[#aab8b4]">{faq.answer}</p>
                  </div>
                ))}
              </div>
            </div>

            <section
              id="contacto"
              className="rounded-[2rem] border border-[#2d4a43] bg-[linear-gradient(180deg,rgba(127,219,192,0.12),rgba(255,181,71,0.08))] p-7"
            >
              <span className="inline-flex rounded-full border border-[#35554d] bg-[#10201d] px-3 py-1 text-xs font-semibold uppercase tracking-[0.24em] text-[#7fdbc0]">
                Contacto
              </span>
              <h2 className="mt-5 text-3xl font-semibold tracking-tight text-white sm:text-4xl">
                Si el negocio necesita mas orden, mas velocidad o mas clientes, aqui empezamos.
              </h2>
              <p className="mt-4 max-w-xl text-sm leading-7 text-[#d4dfdb]">
                Podemos comenzar con una landing comercial, un flujo automatizado o una
                herramienta interna. El objetivo es construir algo util hoy que tambien
                sirva manana.
              </p>
              <div className="mt-8 flex flex-col gap-4 sm:flex-row">
                <Link
                  href="/contact"
                  className="inline-flex h-11 items-center justify-center rounded-lg bg-[#07110f] px-6 text-sm font-medium text-[#7fdbc0] transition hover:bg-[#0d1816]"
                >
                  Solicitar cotizacion
                </Link>
                <Link
                  href="/contact"
                  className="inline-flex h-11 items-center justify-center rounded-lg border border-white/15 bg-white/8 px-6 text-sm font-medium text-white transition hover:bg-white/12"
                >
                  Enviar solicitud
                </Link>
                <a
                  href="https://wa.me/51999999999"
                  target="_blank"
                  rel="noreferrer"
                  className="inline-flex h-11 items-center justify-center rounded-lg border border-white/15 bg-white/8 px-6 text-sm font-medium text-white transition hover:bg-white/12"
                >
                  Escribir por WhatsApp
                </a>
              </div>
              <div className="mt-8 grid gap-4 sm:grid-cols-2">
                <div className="rounded-2xl border border-white/10 bg-[#0a1513] p-5">
                  <p className="text-xs uppercase tracking-[0.2em] text-[#7fdbc0]">Enfoque</p>
                  <p className="mt-2 text-sm leading-7 text-[#b8c6c1]">
                    Captacion, automatizacion y arquitectura preparada para crecer.
                  </p>
                </div>
                <div className="rounded-2xl border border-white/10 bg-[#0a1513] p-5">
                  <p className="text-xs uppercase tracking-[0.2em] text-[#7fdbc0]">Cobertura</p>
                  <p className="mt-2 text-sm leading-7 text-[#b8c6c1]">
                    Soluciones digitales orientadas a PYMES de Peru y Latinoamerica.
                  </p>
                </div>
              </div>
            </section>
          </section>
        </main>

        <footer className="mt-20 flex flex-col gap-6 border-t border-white/8 pt-8 text-sm text-[#8fa19c] md:flex-row md:items-center md:justify-between">
          <div>
            <p className="font-semibold tracking-[0.24em] text-[#7fdbc0]">CODETED</p>
            <p className="mt-2 max-w-xl">
              Plataforma digital en evolucion: sitio comercial, portafolio, CRM, panel administrativo y base para producto SaaS.
            </p>
          </div>
          <div className="flex flex-wrap gap-5">
            <Link href="#servicios" className="transition hover:text-white">Servicios</Link>
            <Link href="#proceso" className="transition hover:text-white">Proceso</Link>
            <Link href="#prueba-social" className="transition hover:text-white">Prueba social</Link>
            <Link href="#contacto" className="transition hover:text-white">Contacto</Link>
          </div>
        </footer>
      </div>
    </div>
  );
}
