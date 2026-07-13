# CodeTED Frontend

Frontend principal de CodeTED construido con Next.js 15, React 19 y TypeScript.

## Alcance actual

El frontend ya no es un proyecto base de `create-next-app`. Hoy incluye:

- Landing comercial premium
- Login y consumo de autenticacion JWT
- Dashboard administrativo
- Modulos de usuarios, roles, leads, servicios, portafolio y blog
- Modulos de clientes, proyectos y cotizaciones
- Portal cliente
- Integracion con backend via API REST

## Stack

- Next.js 15
- React 19
- TypeScript
- Tailwind CSS
- shadcn/ui
- TanStack Query
- Axios

## Estructura relevante

- `app/`: rutas App Router
- `components/`: componentes compartidos y landing
- `features/`: logica por dominio
- `hooks/`: hooks reutilizables
- `lib/`: helpers y utilidades
- `services/`: clientes y consumo de API
- `styles/`: estilos globales y tokens
- `types/`: contratos TypeScript

## Rutas principales

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

## Variables de entorno

1. Copia `frontend/.env.local.example` a `frontend/.env.local`
2. Define como minimo:

```bash
NEXT_PUBLIC_API_URL=http://localhost:8080/api
NEXT_PUBLIC_SITE_URL=http://localhost:3000
```

## Desarrollo local

```bash
npm ci
npm run dev
```

## Validacion

```bash
npm run lint
npm run build
```

## Produccion

El frontend puede ejecutarse:

- en desarrollo con `npm run dev`
- como build standalone con `npm run build`
- dentro de Docker usando `frontend/Dockerfile`

## Nota operativa

Este directorio debe pertenecer al mismo repositorio principal de CodeTED. Si aparece como repositorio Git independiente, debe normalizarse antes de seguir publicando cambios.
