# SYSTEM ARCHITECTURE

Version: 0.2

## Objetivo

Definir la arquitectura de alto nivel del sistema CodeTED.

## Arquitectura aprobada

- Monolito modular en backend
- Frontend separado en Next.js
- Backend REST en Spring Boot
- PostgreSQL como base de datos principal
- Docker Compose para desarrollo y produccion base

## Principios

- Clean Architecture
- SOLID
- DRY
- KISS
- YAGNI
- Vertical Slice

## Estado actual

La arquitectura ya esta implementada y operativa como base de producto:

- Frontend comercial y administrativo en Next.js 15
- Backend Spring Boot 3.5 con JWT, refresh token y RBAC base
- Persistencia PostgreSQL con migraciones Flyway
- Modulos operativos de leads, blog, servicios, portafolio, clientes, proyectos y cotizaciones
- Portal cliente conectado al backend
- Configuracion de despliegue con `docker-compose.prod.yml`

## Componentes

- `frontend/`: experiencia publica, login, dashboard y portal cliente
- `backend/`: API REST, seguridad, reglas de negocio y persistencia
- `database/`: apoyo documental de datos
- `docs/`: arquitectura, seguridad, base de datos y operacion
- `planning/`: backlog, epicas, sprints y QA

## Riesgos actuales

- Falta smoke test final completo en contenedores
- La salida a produccion publica todavia requiere secretos reales, HTTPS y QA manual
