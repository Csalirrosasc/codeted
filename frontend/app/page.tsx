import HomePage from '@/components/landing/home-page';

const organizationJsonLd = {
  '@context': 'https://schema.org',
  '@type': 'Organization',
  name: 'CodeTED',
  url: 'https://codeted.com',
  description:
    'Soluciones digitales para PYMES: landing pages, automatizaciones, CRM y software a medida.',
  areaServed: 'PE',
  knowsAbout: [
    'Landing Pages',
    'CRM',
    'Automatizacion',
    'Desarrollo Web',
    'Inteligencia Artificial',
  ],
};

export default function Page() {
  return (
    <>
      <script
        type="application/ld+json"
        dangerouslySetInnerHTML={{ __html: JSON.stringify(organizationJsonLd) }}
      />
      <HomePage />
    </>
  );
}
