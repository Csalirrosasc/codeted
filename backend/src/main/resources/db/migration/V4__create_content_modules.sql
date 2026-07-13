CREATE TABLE IF NOT EXISTS service_offerings (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(140) NOT NULL,
    slug VARCHAR(160) NOT NULL UNIQUE,
    summary VARCHAR(280) NOT NULL,
    description TEXT,
    featured BOOLEAN NOT NULL DEFAULT FALSE,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS portfolio_items (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(150) NOT NULL,
    slug VARCHAR(170) NOT NULL UNIQUE,
    category VARCHAR(80) NOT NULL,
    summary VARCHAR(280) NOT NULL,
    challenge TEXT,
    solution TEXT,
    result TEXT,
    featured BOOLEAN NOT NULL DEFAULT FALSE,
    published BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS blog_posts (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(160) NOT NULL,
    slug VARCHAR(180) NOT NULL UNIQUE,
    author VARCHAR(100) NOT NULL,
    excerpt VARCHAR(320) NOT NULL,
    content TEXT,
    featured BOOLEAN NOT NULL DEFAULT FALSE,
    published BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO service_offerings (title, slug, summary, description, featured, active)
SELECT
    'Landing Pages Premium',
    'landing-pages-premium',
    'Paginas comerciales pensadas para convertir visitas en reuniones, leads y ventas.',
    'Disenamos experiencias comerciales con copy claro, diseno premium y analitica lista para crecer.',
    TRUE,
    TRUE
WHERE NOT EXISTS (
    SELECT 1 FROM service_offerings WHERE slug = 'landing-pages-premium'
);

INSERT INTO service_offerings (title, slug, summary, description, featured, active)
SELECT
    'Automatizacion de procesos',
    'automatizacion-de-procesos',
    'Flujos conectados a formularios, correo y WhatsApp para reducir trabajo manual.',
    'Implementamos automatizaciones de negocio para ventas, seguimiento comercial y operacion interna.',
    TRUE,
    TRUE
WHERE NOT EXISTS (
    SELECT 1 FROM service_offerings WHERE slug = 'automatizacion-de-procesos'
);

INSERT INTO service_offerings (title, slug, summary, description, featured, active)
SELECT
    'Sistemas internos',
    'sistemas-internos',
    'CRM, paneles y herramientas a medida para ordenar la operacion del negocio.',
    'Construimos modulos internos sobre una base mantenible en Next.js y Spring Boot.',
    TRUE,
    TRUE
WHERE NOT EXISTS (
    SELECT 1 FROM service_offerings WHERE slug = 'sistemas-internos'
);

INSERT INTO service_offerings (title, slug, summary, description, featured, active)
SELECT
    'IA aplicada',
    'ia-aplicada',
    'Soluciones con IA para acelerar atencion, clasificacion y seguimiento comercial.',
    'Aplicamos IA de forma util a tareas reales: respuesta, clasificacion, apoyo operativo y automatizacion.',
    FALSE,
    TRUE
WHERE NOT EXISTS (
    SELECT 1 FROM service_offerings WHERE slug = 'ia-aplicada'
);

INSERT INTO portfolio_items (title, slug, category, summary, challenge, solution, result, featured, published)
SELECT
    'Landing comercial para servicios profesionales',
    'landing-servicios-profesionales',
    'Landing',
    'Sitio orientado a conversion con CTA claros, prueba social y enfoque comercial.',
    'El negocio tenia presencia digital debil y baja claridad de propuesta.',
    'Se construyo una landing premium con arquitectura lista para crecer a CRM.',
    'Mejor base para captacion, reuniones y medicion de interacciones.',
    TRUE,
    TRUE
WHERE NOT EXISTS (
    SELECT 1 FROM portfolio_items WHERE slug = 'landing-servicios-profesionales'
);

INSERT INTO portfolio_items (title, slug, category, summary, challenge, solution, result, featured, published)
SELECT
    'Panel operativo para seguimiento interno',
    'panel-operativo-seguimiento-interno',
    'Dashboard',
    'Panel modular para clientes, proyectos, cotizaciones y contenido.',
    'La operacion dependia de seguimiento manual y datos dispersos.',
    'Se centralizo la gestion en un dashboard con auth JWT y modulos conectados.',
    'Mas orden operacional y mejor base para evolucion a portal cliente.',
    TRUE,
    TRUE
WHERE NOT EXISTS (
    SELECT 1 FROM portfolio_items WHERE slug = 'panel-operativo-seguimiento-interno'
);

INSERT INTO blog_posts (title, slug, author, excerpt, content, featured, published)
SELECT
    'Introduccion a Next.js 15 y React 19',
    'introduccion-nextjs-15-react-19',
    'CodeTED',
    'Descubre las novedades de Next.js 15 y React 19 en esta guia rapida.',
    'Una mirada practica a los cambios que importan para productos modernos orientados a conversion y escalabilidad.',
    TRUE,
    TRUE
WHERE NOT EXISTS (
    SELECT 1 FROM blog_posts WHERE slug = 'introduccion-nextjs-15-react-19'
);

INSERT INTO blog_posts (title, slug, author, excerpt, content, featured, published)
SELECT
    'Arquitectura limpia con Spring Boot 3.5',
    'arquitectura-limpia-spring-boot-3-5',
    'CodeTED',
    'Aprende a estructurar aplicaciones empresariales en Java de manera profesional.',
    'Explicamos como separar controladores, servicios, repositorios y contratos para crecer sin reescribir.',
    FALSE,
    TRUE
WHERE NOT EXISTS (
    SELECT 1 FROM blog_posts WHERE slug = 'arquitectura-limpia-spring-boot-3-5'
);
