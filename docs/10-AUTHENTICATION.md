# AUTHENTICATION

Version: 0.2

## Flujo actual

1. El usuario hace login con `username` y `password`
2. El backend devuelve:
   - access token JWT
   - refresh token persistido
   - perfil base del usuario
3. El frontend guarda ambos tokens
4. Cuando el access token expira, el frontend intenta `POST /api/auth/refresh`
5. Si el refresh token es valido, la sesion continua
6. Si falla, el frontend limpia sesion y redirige al login

## Endpoints

- `POST /api/auth/login`
- `POST /api/auth/refresh`
- `POST /api/auth/logout`
- `GET /api/auth/me`

## Tipos de acceso

- Panel administrativo: usuarios con permisos administrativos
- Portal cliente: usuarios con permiso `portal.read.own`

## Perfil de desarrollo

- Usuario inicial: `admin`
- Password inicial: `admin123`
