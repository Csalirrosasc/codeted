# CodeTED

Plataforma full-stack orientada a construir la presencia comercial y el producto SaaS de CodeTED.

## Estado actual

El repositorio ya cuenta con una base operativa real:

- Infraestructura local con PostgreSQL y pgAdmin
- Backend Spring Boot con JWT, refresh token, RBAC base, Flyway, Swagger y contrato unificado
- Frontend Next.js con landing comercial, dashboard operativo, paneles administrativos y portal cliente

## Estructura

- `frontend/`: Next.js 15, React 19, TypeScript, Tailwind CSS, shadcn/ui
- `backend/`: Java 21, Spring Boot 3.5, Spring Security, Flyway, OpenAPI
- `docs/`: documentacion funcional y arquitectonica
- `planning/`: backlog, epicas y sprints
- `ai/`: reglas y contexto de trabajo para agentes
- `docker/`: recursos de infraestructura complementarios

## Requisitos

- Node.js 20+
- Java JDK 21
- Docker Desktop con Docker Compose

## Variables de entorno

1. Copia `.env.example` a `.env` en la raiz
2. Copia `frontend/.env.local.example` a `frontend/.env.local`

## Levantar el proyecto

### Base de datos

```bash
docker compose up -d
```

### Backend

```bash
cd backend
./mvnw spring-boot:run
```

### Frontend

```bash
cd frontend
npm ci
npm run dev
```

## URLs locales

- Frontend: `http://localhost:3000`
- Backend API: `http://localhost:8080/api`
- Swagger UI: `http://localhost:8080/api/swagger-ui.html`
- pgAdmin: `http://localhost:5050`
- Portal cliente: `http://localhost:3000/client-portal`

## Flujo de acceso disponible

- Login frontend: `http://localhost:3000/auth/login`
- Dashboard protegido: `http://localhost:3000/dashboard`
- Usuarios: `http://localhost:3000/dashboard/users`
- Roles: `http://localhost:3000/dashboard/roles`
- Leads: `http://localhost:3000/dashboard/leads`
- Usuario inicial: `admin`
- Contrasena inicial: `admin123`

## Modulos ya disponibles

- Autenticacion JWT
- Roles y permisos base
- Servicios
- Portafolio
- Blog
- Leads/contacto
- Clientes
- Proyectos
- Cotizaciones
- Portal cliente

## Produccion con Docker Compose

```bash
docker compose -f docker-compose.prod.yml --env-file .env up --build -d
```

Si `3000` o `8080` ya estan ocupados, ajusta en `.env`:

```bash
BACKEND_PORT=8081
FRONTEND_PORT=3001
NEXT_PUBLIC_API_URL=http://localhost:8081/api
NEXT_PUBLIC_SITE_URL=http://localhost:3001
```

## Guia de despliegue

Lee este orden para levantarlo en otra computadora:

1. `README.md`
2. `docs/03-SYSTEM_ARCHITECTURE.md`
3. `docs/11-PRODUCTION_OPERATIONS.md`
4. `docs/12-DEPLOYMENT_GUIDE.md`
5. `docs/13-VPS_DEPLOYMENT.md`
6. `docs/14-NGINX_HTTPS_SETUP.md`
7. `docs/15-DUCKDNS_SINGLE_DOMAIN.md`
8. `planning/QA_CHECKLIST.md`

## Notas

- La documentacion viva del proyecto esta en `docs/`, `planning/` y `ai/`
- Si detectas contradicciones entre documentacion y codigo, prioriza resolverlas antes de nuevas funcionalidades
