# NGINX HTTPS SETUP

Version: 0.1

## Objetivo

Dejar una base practica para publicar CodeTED con Nginx y HTTPS usando dos subdominios:

- `app.tudominio.com` para frontend
- `api.tudominio.com` para backend

## Requisitos

- VPS Linux con Docker funcionando
- DNS apuntando al servidor
- Stack levantado con `docker-compose.prod.yml`
- Puertos locales accesibles para Nginx:
  - `FRONTEND_PORT`
  - `BACKEND_PORT`

## Variables recomendadas

En `.env.production.example` o en tu `.env` real:

```bash
NEXT_PUBLIC_SITE_URL=https://app.tudominio.com
NEXT_PUBLIC_API_URL=https://api.tudominio.com/api
CORS_ALLOWED_ORIGINS=https://app.tudominio.com
BACKEND_PORT=8080
FRONTEND_PORT=3000
```

## Instalar Nginx y Certbot

```bash
sudo apt update
sudo apt install -y nginx certbot python3-certbot-nginx
```

## Configuracion Nginx

Crea un archivo como:

`/etc/nginx/sites-available/codeted`

con este contenido:

```nginx
server {
    server_name app.tudominio.com;

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

server {
    server_name api.tudominio.com;

    location / {
        proxy_pass http://127.0.0.1:8080;
        proxy_http_version 1.1;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;
    }
}
```

Si cambiaste los puertos del compose, reemplaza `3000` y `8080` por tus valores reales.

## Activar el sitio

```bash
sudo ln -s /etc/nginx/sites-available/codeted /etc/nginx/sites-enabled/codeted
sudo nginx -t
sudo systemctl reload nginx
```

## Emitir certificados HTTPS

```bash
sudo certbot --nginx -d app.tudominio.com -d api.tudominio.com
```

Certbot ajustara automaticamente el archivo para HTTPS si detecta una configuracion valida.

## Verificaciones

- `https://app.tudominio.com`
- `https://api.tudominio.com/api/health`
- login funcionando desde frontend
- peticiones del frontend llegando al backend sin error CORS

## Ajustes recomendados

- deshabilitar puertos publicos innecesarios en firewall salvo `80` y `443`
- limitar acceso SSH por IP o llave
- revisar renovacion automatica de certificados:

```bash
sudo certbot renew --dry-run
```
