# FRONTEND ARCHITECTURE

Version: 0.2

## Stack

- Next.js 15
- React 19
- TypeScript
- Tailwind CSS
- shadcn/ui
- TanStack Query
- Axios

## Reglas

- App Router obligatorio
- Server Components por defecto
- Client Components solo cuando aporten interactividad real
- No usar `any`
- Consumir backend via `NEXT_PUBLIC_API_URL`

## Estado actual

El frontend ya opera como aplicacion comercial y transaccional completa sobre App Router.

Estado implementado:

- Landing comercial con narrativa de marca, CTAs y prueba social
- Login operativo conectado a autenticacion JWT del backend
- Dashboard administrativo protegido
- Modulos administrativos para usuarios, roles, leads, servicios, portafolio y blog
- Modulos comerciales para clientes, proyectos y cotizaciones
- Portal cliente para visualizar informacion asociada al cliente autenticado
- Consumo de API REST con base URL configurable por variables de entorno
- Build de produccion tipo standalone para despliegue en contenedor

## Rutas implementadas

- `/`
- `/auth/login`
- `/contact`
- `/dashboard`
- `/dashboard/users`
- `/dashboard/roles`
- `/dashboard/leads`
- `/dashboard/services`
- `/dashboard/portfolio`
- `/dashboard/blog`
- `/dashboard/clients`
- `/dashboard/projects`
- `/dashboard/quotes`
- `/client-portal`

## Riesgos actuales

- Aun depende de QA funcional manual por modulo
- Falta validacion final en entorno productivo real
- Requiere mantener alineadas las variables `NEXT_PUBLIC_API_URL` y `NEXT_PUBLIC_SITE_URL`
