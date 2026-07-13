# SECURITY

Version: 0.2

## Modelo

- Autenticacion JWT stateless
- Refresh token persistido
- RBAC basado en roles y permisos
- Rutas publicas separadas de panel y portal cliente

## Roles base

- `ROLE_ADMIN`
- `ROLE_EDITOR`
- `ROLE_CLIENT`

## Permisos base

- `users.read`
- `users.write`
- `roles.read`
- `roles.write`
- `leads.read`
- `leads.write`
- `customers.read`
- `customers.write`
- `projects.read`
- `projects.write`
- `quotes.read`
- `quotes.write`
- `blog.read`
- `blog.write`
- `services.read`
- `services.write`
- `portfolio.read`
- `portfolio.write`
- `dashboard.read`
- `portal.read.own`

## Reglas operativas

- `ADMIN` administra todo el sistema
- `EDITOR` administra contenido y puede ver leads
- `CLIENT` solo accede a su propio portal
- El portal cliente nunca comparte permisos del panel admin

## Rutas publicas

- `POST /api/auth/login`
- `POST /api/auth/refresh`
- `GET /api/services`
- `GET /api/portfolio`
- `GET /api/blog/posts`
- `POST /api/contact/requests`

## Consideraciones pendientes

- Auditoria avanzada
- Rotacion automatizada de secretos
- politicas de bloqueo/rate limiting
