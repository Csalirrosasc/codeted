# DUCKDNS SINGLE DOMAIN

Version: 0.1

## Objetivo

Publicar CodeTED usando un solo dominio DuckDNS:

- `codeted.duckdns.org` para frontend
- `codeted.duckdns.org/api` para backend

## Cuando usar esta guia

Usa esta opcion si quieres probar gratis ya mismo y solo tienes un subdominio DuckDNS.

## Variables recomendadas

Parte de:

```bash
cp .env.duckdns.example .env
```

La configuracion clave es:

```bash
CORS_ALLOWED_ORIGINS=https://codeted.duckdns.org
NEXT_PUBLIC_API_URL=https://codeted.duckdns.org/api
NEXT_PUBLIC_SITE_URL=https://codeted.duckdns.org
```

## Levantar el stack

```bash
docker compose -f docker-compose.prod.yml --env-file .env up --build -d
```

## Nginx con un solo dominio

Crea:

`/etc/nginx/sites-available/codeted`

con este contenido:

```nginx
server {
    server_name codeted.duckdns.org;

    location /api/ {
        proxy_pass http://127.0.0.1:8080/api/;
        proxy_http_version 1.1;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;
    }

    location / {
        proxy_pass http://127.0.0.1:3000;
        proxy_http_version 1.1;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;
        proxy_set_header Upgrade $http_upgrade;
        proxy_set_header Connection "upgrade";
    }
}
```

## Activar Nginx

```bash
sudo ln -s /etc/nginx/sites-available/codeted /etc/nginx/sites-enabled/codeted
sudo nginx -t
sudo systemctl reload nginx
```

## HTTPS con Certbot

```bash
sudo certbot --nginx -d codeted.duckdns.org
```

## Verificaciones

- `https://codeted.duckdns.org`
- `https://codeted.duckdns.org/api/health`
- login cargando
- dashboard cargando
- formulario de contacto funcionando

## Limitaciones

- DuckDNS sirve bien para pruebas y demo
- no es lo ideal para branding serio a largo plazo
- si cambias de IP publica, debes actualizar DuckDNS
