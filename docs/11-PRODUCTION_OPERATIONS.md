# PRODUCTION OPERATIONS

Version: 0.1

## Objetivo

Este documento resume el endurecimiento minimo recomendado antes de exponer CodeTED a trafico real.

## Checklist tecnico

- Cambiar secretos JWT por valores largos y unicos
- Configurar `CORS_ALLOWED_ORIGINS` con dominios reales
- Configurar `NEXT_PUBLIC_API_URL` y `NEXT_PUBLIC_SITE_URL`
- Configurar `AUTH_RATE_LIMIT_PER_MINUTE` y `CONTACT_RATE_LIMIT_PER_MINUTE`
- Ejecutar `docker compose -f docker-compose.prod.yml --env-file .env up --build`
- Verificar migraciones Flyway en arranque
- Validar login admin y portal cliente
- Confirmar que `/contact` registra leads reales
- Confirmar healthchecks en `postgres`, `backend` y `frontend`

## Hardening recomendado

- Reverse proxy con HTTPS
- Rate limiting para login y contacto
- Headers de seguridad en frontend y backend
- Logs centralizados
- Backups de PostgreSQL
- Monitoreo basico de CPU, memoria y disco
- Politica de rotacion de credenciales

## Validaciones previas a salida

- `backend`: `./mvnw test`
- `frontend`: `npm run lint`
- `frontend`: `npm run build`

## Riesgos residuales

- Cobertura automatizada aun parcial
- Sin auditoria avanzada de acciones
- Sin pipeline de smoke tests post-deploy
- Falta validacion manual final sobre el servidor real de destino
