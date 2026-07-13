# API SPECIFICATION

Version: 0.2

## Convenciones

- Base path: `/api`
- Documentacion Swagger: `/api/swagger-ui.html`
- Respuesta exitosa: `ApiResponse<T>`
- Respuesta de error: `ErrorResponse`

## Endpoints actuales

- `GET /api/services`
- `GET /api/services/admin`
- `POST /api/services`
- `PATCH /api/services/{publicId}`
- `DELETE /api/services/{publicId}`
- `GET /api/portfolio`
- `GET /api/portfolio/admin`
- `POST /api/portfolio`
- `PATCH /api/portfolio/{publicId}`
- `DELETE /api/portfolio/{publicId}`
- `GET /api/blog/posts`
- `GET /api/blog/admin/posts`
- `POST /api/blog/posts`
- `PATCH /api/blog/posts/{publicId}`
- `DELETE /api/blog/posts/{publicId}`
- `POST /api/auth/login`
- `POST /api/auth/refresh`
- `POST /api/auth/logout`
- `GET /api/auth/me`
- `GET /api/users`
- `POST /api/users`
- `PATCH /api/users/{publicId}`
- `GET /api/roles`
- `PATCH /api/roles/{publicId}/permissions`
- `POST /api/contact/requests`
- `GET /api/contact/leads`
- `PATCH /api/contact/leads/{publicId}/status`
- `DELETE /api/contact/leads/{publicId}`
- `GET /api/dashboard/summary`
- `GET /api/client-portal/me`
- `GET /api/customers`
- `POST /api/customers`
- `GET /api/projects`
- `POST /api/projects`
- `GET /api/quotes`
- `POST /api/quotes`

## Pendiente

Completar operaciones avanzadas de clientes, proyectos y cotizaciones, mas automatizacion comercial y configuracion del sistema.
