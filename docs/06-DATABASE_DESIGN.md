# DATABASE DESIGN

Version: 0.2

## Base de datos principal

- PostgreSQL
- Migraciones con Flyway

## Estado actual

Migraciones implementadas actualmente:

- `V1__create_users_table.sql`
- `V2__create_refresh_tokens.sql`
- `V3__create_crm_project_quote_tables.sql`
- `V4__create_content_modules.sql`
- `V5__rbac_portal_uuid_contact.sql`

Tablas activas:

- `users`
- `refresh_tokens`
- `customers`
- `projects`
- `quotes`
- `service_offerings`
- `portfolio_items`
- `blog_posts`
- `roles`
- `permissions`
- `user_roles`
- `role_permissions`
- `contact_leads`

## Nota importante

La implementacion actual mantiene `BIGSERIAL` como clave interna, pero ya incorpora `public_id UUID` como identificador canonico expuesto a nivel de dominio y API.
