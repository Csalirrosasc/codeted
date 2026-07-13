# Docker Resources

Este directorio agrupa recursos auxiliares de contenedores para el proyecto.

## Uso actual

- `docker-compose.yml` en la raiz: stack local de desarrollo
- `docker-compose.prod.yml` en la raiz: stack base de produccion

## Flujo recomendado

### Desarrollo

```bash
docker compose up -d
```

### Produccion base

```bash
docker compose -f docker-compose.prod.yml --env-file .env up --build
```

## Nota

Antes de desplegar en produccion real:

- define secretos y dominios reales
- verifica healthchecks
- ejecuta QA funcional
- usa HTTPS con reverse proxy
