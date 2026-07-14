# VPS DEPLOYMENT

Version: 0.1

## Objetivo

Dejar el paso a paso minimo para desplegar CodeTED en un VPS Linux con Docker.

## Escenario recomendado

- Ubuntu 22.04 o 24.04
- Docker Engine instalado
- Docker Compose disponible
- Dominio para frontend, por ejemplo `app.tudominio.com`
- Dominio para API, por ejemplo `api.tudominio.com`
- Reverse proxy con HTTPS delante del stack

## Archivos a leer antes

1. `README.md`
2. `docs/11-PRODUCTION_OPERATIONS.md`
3. `docs/12-DEPLOYMENT_GUIDE.md`
4. `docs/14-NGINX_HTTPS_SETUP.md`
5. `planning/QA_CHECKLIST.md`

## 1. Preparar el servidor

Instala como minimo:

```bash
sudo apt update
sudo apt install -y git curl
```

Instala Docker y Compose segun tu metodo preferido.

## 2. Clonar el proyecto

```bash
git clone https://github.com/Csalirrosasc/codeted.git
cd codeted
```

## 3. Crear el archivo `.env`

Usa la plantilla de produccion:

```bash
cp .env.production.example .env
```

Luego reemplaza:

- `POSTGRES_PASSWORD`
- `JWT_SECRET`
- `CORS_ALLOWED_ORIGINS`
- `NEXT_PUBLIC_API_URL`
- `NEXT_PUBLIC_SITE_URL`

## 4. Levantar el stack

```bash
docker compose -f docker-compose.prod.yml --env-file .env up --build -d
```

## 5. Verificar el stack

```bash
docker compose -f docker-compose.prod.yml --env-file .env ps
docker compose -f docker-compose.prod.yml --env-file .env logs -f
```

Verifica:

- frontend respondiendo
- backend saludable en `/api/health`
- login cargando
- formulario de contacto operativo

## 6. Reverse proxy y HTTPS

Este repositorio no instala automaticamente Nginx o Traefik.

Necesitas:

- publicar `app.tudominio.com` hacia `FRONTEND_PORT`
- publicar `api.tudominio.com` hacia `BACKEND_PORT`
- emitir certificados TLS

Guia sugerida:

- revisa `docs/14-NGINX_HTTPS_SETUP.md`

## 7. Actualizar despliegue

Cuando subas nuevos cambios:

```bash
git pull origin main
docker compose -f docker-compose.prod.yml --env-file .env up --build -d
```

## 8. Respaldo minimo

- respalda el volumen de PostgreSQL
- conserva una copia segura del `.env`
- rota `JWT_SECRET` solo con ventana de mantenimiento si ya hay sesiones activas

## 9. Checklist final

- secretos reales configurados
- dominios reales configurados
- HTTPS activo
- healthchecks en verde
- QA manual completado
