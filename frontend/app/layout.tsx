import type { Metadata } from 'next';
import { Inter } from 'next/font/google';
import './globals.css';
import Providers from '@/components/providers';

/**
 * Inter is the project's primary typeface.
 * Geist was the Next.js default; replaced to align with the design system.
 */
const inter = Inter({
  variable: '--font-inter',
  subsets: ['latin'],
  display: 'swap',
});

export const metadata: Metadata = {
  title: {
    default: 'CodeTED | Soluciones Digitales para tu Negocio',
    template: '%s | CodeTED',
  },
  description:
    'CodeTED ofrece soluciones tecnológicas profesionales para pequeñas y medianas empresas: Landing Pages, CRM, Panel Administrativo, Automatizaciones e Inteligencia Artificial.',
  keywords: [
    'desarrollo web',
    'software a medida',
    'CRM',
    'automatización',
    'PYMES',
    'Latinoamérica',
    'CodeTED',
  ],
  authors: [{ name: 'CodeTED' }],
  creator: 'CodeTED',
  metadataBase: new URL(process.env.NEXT_PUBLIC_SITE_URL || 'http://localhost:3000'),
  openGraph: {
    type: 'website',
    locale: 'es_PE',
    title: 'CodeTED | Soluciones Digitales para tu Negocio',
    description:
      'Transformamos ideas en soluciones digitales que generan resultados reales.',
    siteName: 'CodeTED',
  },
  robots: {
    index: true,
    follow: true,
  },
};

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <html lang="es">
      <body className={`${inter.variable} antialiased`}>
        <Providers>{children}</Providers>
      </body>
    </html>
  );
}
