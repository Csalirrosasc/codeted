# QA CHECKLIST

## Acceso

- Login con `admin/admin123`
- Refresh token al expirar sesion
- Logout funcional

## Landing

- Hero visible en desktop y mobile
- CTA principal lleva a `/contact`
- CTA de contacto inferior lleva a `/contact`
- Insights cargan desde backend

## Leads

- Formulario `/contact` crea lead
- Lead visible en `/dashboard/leads`

## Admin

- `/dashboard/users` lista usuarios
- `/dashboard/roles` lista permisos
- `/dashboard/clients` crea, edita y elimina
- `/dashboard/projects` crea, edita y elimina
- `/dashboard/quotes` crea, edita y elimina
- `/dashboard/services` crea y elimina
- `/dashboard/portfolio` crea y elimina
- `/dashboard/blog` crea y elimina

## Portal cliente

- Usuario cliente autenticado entra a `/client-portal`
- Solo ve proyectos y cotizaciones de su cliente asociado

## Seguridad

- Rutas protegidas redirigen a login sin token
- Usuario cliente no debe usar panel admin si no tiene permisos
- Login bloquea exceso de intentos segun rate limit
- `/contact` bloquea spam segun rate limit
- `/api/health` responde OK en entorno desplegado

## Build

- `./mvnw test`
- `npm run lint`
- `npm run build`
