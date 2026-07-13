# Contexto del Proyecto - CodeTED

> Este archivo es la fuente de verdad rapida para agentes IA.
> Para detalles completos, ver `docs/00-README.md` y `docs/01-PROJECT_VISION.md`.

---

## Que es CodeTED

CodeTED es una plataforma SaaS B2B de desarrollo de software para pequenas y medianas empresas latinoamericanas.

**No es una pagina web corporativa.** Es un ecosistema completo con:
- Landing Page Premium
- CRM interno
- Panel administrativo
- Portal para clientes
- Blog con SEO
- Sistema de cotizaciones
- Automatizaciones

**Propuesta de valor**: "Software profesional con calidad empresarial, disenado especificamente para pequenas y medianas empresas latinoamericanas."

---

## Estado actual del proyecto

**Version**: `0.1.0-SNAPSHOT`  
**Fase actual**: Fase 2 - Base operativa del producto  
**Siguiente fase**: Fase 3 - Portal cliente y RBAC ampliado

### Lo que ya esta implementado

- Estructura completa de frontend y backend
- Stack base configurado: Next.js 15 + Spring Boot 3.5 + PostgreSQL
- Docker Compose para desarrollo y compose de produccion
- Flyway con migraciones V1 a V4
- Seguridad JWT con login, refresh, logout y perfil autenticado
- Swagger/OpenAPI en `/api/swagger-ui.html`
- Landing comercial inicial
- Dashboard base protegido
- CRM basico de clientes
- Gestion basica de proyectos
- Gestion basica de cotizaciones
- Catalogo comercial de servicios
- Portafolio persistido
- Blog persistido con endpoints publicos y administrativos

### Lo pendiente real

- Operaciones avanzadas en clientes, proyectos y cotizaciones
- Configuracion funcional del sistema
- Tests por modulo

---

## Modulos del backend

| Paquete | Estado | Descripcion |
|---|---|---|
| `common` | Activo | BaseEntity, ApiResponse, ErrorResponse, GlobalExceptionHandler |
| `security` | Activo | JWT filter, CORS y seguridad stateless |
| `auth` | Activo | Login, refresh token, logout, perfil y administracion de usuarios/roles |
| `blog` | Activo | Blog persistido con endpoints publicos y admin |
| `contact` | Activo | Captacion publica y leads administrativos |
| `crm` | Activo | CRM basico de clientes |
| `portfolio` | Activo | Casos y activos de portafolio |
| `portal` | Activo | Portal cliente para proyectos y cotizaciones propias |
| `project` | Activo | Gestion interna de proyectos |
| `quote` | Activo | Sistema base de cotizaciones |
| `servicecatalog` | Activo | Catalogo comercial de servicios |

---

## Modulos del frontend

| Carpeta | Estado | Descripcion |
|---|---|---|
| `app/` | Parcial | Landing, login y dashboard con modulos operativos |
| `components/ui/` | Activo | Componentes UI base |
| `lib/api-client.ts` | Activo | Axios con manejo de JWT y refresh |
| `types/index.ts` | Activo | Tipos globales del contrato API |
| `features/` | Vacio | Vertical slices pendientes |
| `services/` | Vacio | Servicios API por dominio pendientes |
| `hooks/` | Vacio | Hooks personalizados pendientes |

---

## URLs locales

| Servicio | URL |
|---|---|
| Frontend | http://localhost:3000 |
| Backend API | http://localhost:8080/api |
| Swagger UI | http://localhost:8080/api/swagger-ui.html |
| pgAdmin | http://localhost:5050 |
| PostgreSQL | localhost:5432 / db: codeted |

### Credenciales de desarrollo

- PostgreSQL: `codeted_user` / `codeted_password`
- pgAdmin: `admin@codeted.com` / `admin123`
- Usuario admin: `admin` / `admin123`

---

## Decisiones arquitectonicas registradas

| # | Decision | Razon |
|---|---|---|
| ADR-001 | Monolito modular en v1 | Simplicidad y velocidad de desarrollo |
| ADR-002 | PostgreSQL como unica base de datos | YAGNI hasta que exista necesidad real de otra capa |
| ADR-003 | JWT stateless | Compatibilidad con despliegue simple y escalable |
| ADR-004 | Vertical Slice + Clean Architecture | Modulos escalables sin reescritura total |
| ADR-005 | Eliminacion de `@base-ui/react` | Se reemplazo por implementacion compatible con el stack oficial |

---

## Roadmap de fases

| Fase | Objetivo | Estado |
|---|---|---|
| Fase 1 | Landing Page Premium | Base implementada |
| Fase 2 | CRM interno y panel operativo | Base implementada |
| Fase 3 | Area cliente y RBAC ampliado | Base implementada |
| Fase 4 | Automatizaciones e IA | Pendiente |
| Fase 5 | Marketplace y extensiones | Pendiente |
