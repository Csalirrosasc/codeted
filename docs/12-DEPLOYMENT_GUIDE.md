# DEPLOYMENT GUIDE

Version: 0.1

## Objetivo

Dejar una guia unica para levantar CodeTED en otra computadora o servidor.

## Orden de lectura recomendado

1. `README.md`
2. `docs/03-SYSTEM_ARCHITECTURE.md`
3. `docs/11-PRODUCTION_OPERATIONS.md`
4. `planning/QA_CHECKLIST.md`

## Requisitos

- Docker Desktop o Docker Engine con Docker Compose
- Git
- Puerto `3000` libre
- Puerto `8080` libre
- Puerto `5432` libre si usaras PostgreSQL expuesto localmente

## Paso a paso en otra computadora

### 1. Clonar el repositorio

```bash
git clone https://github.com/Csalirrosasc/codeted.git
cd codeted
```

### 2. Preparar variables de entorno

```bash
cp .env.example .env
```

En Windows PowerShell, si `cp` no funciona como alias:

```powershell
Copy-Item .env.example .env
```

### 3. Ajustar valores reales

Configura como minimo:

- `POSTGRES_PASSWORD`
- `JWT_SECRET`
- `CORS_ALLOWED_ORIGINS`
- `NEXT_PUBLIC_API_URL`
- `NEXT_PUBLIC_SITE_URL`

## Despliegue base con Docker

```bash
docker compose -f docker-compose.prod.yml --env-file .env up --build -d
```

Nota:

- `docker-compose.yml` usa el proyecto `codeted-dev`
- `docker-compose.prod.yml` usa el proyecto `codeted-prod`
- asi puedes diferenciar facilmente desarrollo y produccion base en la misma maquina

## Verificacion posterior

- Frontend: `http://localhost:3000`
- Backend health: `http://localhost:8080/api/health`
- Login: `http://localhost:3000/auth/login`

## Usuario inicial

- Usuario: `admin`
- Contrasena: `admin123`

## Comandos utiles

### Ver logs

```bash
docker compose -f docker-compose.prod.yml --env-file .env logs -f
```

### Detener stack

```bash
docker compose -f docker-compose.prod.yml --env-file .env down
```

### Reconstruir desde cero

```bash
docker compose -f docker-compose.prod.yml --env-file .env up --build -d
```

## Checklist antes de publicar a internet

- Cambiar credenciales y secretos por valores reales
- Configurar dominio y HTTPS
- Ejecutar checklist de `planning/QA_CHECKLIST.md`
- Verificar login admin, portal cliente y formulario de contacto
