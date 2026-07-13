# BUSINESS REQUIREMENTS

Version: 2.0

Estado: Base funcional especificada

Proyecto: CodeTED

Documento: Requerimientos de Negocio

---

# 1. Objetivo

Este documento define los requerimientos funcionales y no funcionales de la primera version operativa de CodeTED.

Sera utilizado por:

- Arquitectura
- Backend
- Frontend
- UX/UI
- QA
- DevOps
- Agentes IA

---

# 2. Alcance

La primera version de CodeTED incluye:

- Landing Page Premium
- Gestion de Servicios
- Portafolio
- Blog
- Formulario de Contacto y Leads
- Panel Administrativo
- Dashboard
- Gestion de Clientes
- Gestion de Proyectos
- Gestion de Cotizaciones
- Gestion de Usuarios
- Gestion de Roles y Permisos
- Portal del Cliente

Fuera de alcance en v1:

- Marketplace
- Facturacion electronica
- Multiempresa
- Integraciones ERP
- Automatizaciones avanzadas
- IA transaccional profunda

---

# 3. Objetivos del negocio

- Conseguir clientes
- Mostrar credibilidad
- Mostrar portafolio
- Centralizar la operacion comercial
- Administrar proyectos y cotizaciones
- Reducir procesos manuales
- Crear una base real para ingresos recurrentes

---

# 4. Tipos de usuarios

## Visitante

No requiere autenticacion.

Puede:

- Ver landing
- Ver servicios
- Ver portafolio
- Ver blog
- Enviar contacto
- Solicitar cotizacion

## Cliente

Requiere autenticacion.

Puede:

- Ver su perfil
- Ver sus proyectos
- Ver sus cotizaciones
- Consultar estado
- Enviar mensajes/contacto

No puede:

- Acceder al panel administrativo
- Ver informacion de otros clientes

## Editor

Puede:

- Administrar blog
- Administrar portafolio
- Administrar servicios
- Ver leads

No puede:

- Administrar usuarios
- Administrar roles
- Administrar configuracion critica

## Administrador

Control total del sistema.

Puede administrar:

- Usuarios
- Roles
- Permisos
- Clientes
- Proyectos
- Cotizaciones
- Leads
- Blog
- Servicios
- Portafolio
- Dashboard

---

# 5. Modulos

Cada modulo debe seguir la arquitectura actual del proyecto:

- Controller
- Service
- Repository
- DTO
- Entity
- Mapper
- Validacion
- Tests

Modulos v1:

1. Landing
2. Servicios
3. Portafolio
4. Blog
5. Contacto y Leads
6. Dashboard
7. Clientes
8. Proyectos
9. Cotizaciones
10. Usuarios
11. Roles y Permisos
12. Portal Cliente

---

# 6. Landing Page

Objetivo:

- Generar prospectos
- Mostrar credibilidad
- Convertir visitas en reuniones o solicitudes

La landing debe incluir:

- Hero
- CTA principal
- Servicios
- Beneficios
- Portafolio
- FAQ
- Blog
- Contacto
- Footer

Botones principales:

- Solicitar cotizacion
- Agendar reunion
- Escribir por WhatsApp

Requisito:

- Los CTA deben poder conectarse a eventos analiticos

---

# 7. Gestion de Servicios

Cada servicio debe tener:

- UUID publico
- Nombre
- Slug
- Descripcion corta
- Descripcion completa
- Estado
- Destacado
- Fecha de creacion
- Fecha de modificacion

Acciones:

- Crear
- Editar
- Eliminar
- Activar/Inactivar
- Listar

---

# 8. Portafolio

Cada caso de portafolio debe tener:

- UUID publico
- Titulo
- Slug
- Categoria
- Resumen
- Reto
- Solucion
- Resultado
- Estado de publicacion
- Destacado

Acciones:

- Crear
- Editar
- Eliminar
- Publicar/Despublicar
- Listar

---

# 9. Blog

Cada articulo debe tener:

- UUID publico
- Titulo
- Slug
- Autor
- Extracto
- Contenido
- Estado de publicacion
- Destacado

Acciones:

- Crear
- Editar
- Eliminar
- Publicar/Despublicar
- Listar

---

# 10. Contacto y Leads

Cada lead debe registrar:

- UUID publico
- Nombre
- Email
- Telefono
- Empresa
- Mensaje
- Origen
- Estado

Estados minimos:

- new
- contacted
- qualified
- closed

Acciones:

- Registro publico
- Listado administrativo
- Cambio de estado
- Eliminacion

---

# 11. Dashboard

Debe mostrar metricas base:

- Usuarios
- Sesiones activas
- Leads
- Clientes
- Proyectos
- Cotizaciones
- Posts publicados

Debe servir como punto de entrada a los modulos administrativos.

---

# 12. Clientes

Cada cliente debe tener:

- UUID publico
- Nombre
- Email
- Telefono
- Empresa
- Estado
- Notas

Acciones:

- Crear
- Editar
- Eliminar
- Listar

---

# 13. Proyectos

Cada proyecto debe tener:

- UUID publico
- Cliente asociado
- Nombre
- Descripcion
- Estado
- Fecha inicio
- Fecha fin

Estados minimos:

- planning
- in_progress
- on_hold
- done

Acciones:

- Crear
- Editar
- Eliminar
- Listar

---

# 14. Cotizaciones

Cada cotizacion debe tener:

- UUID publico
- Cliente asociado
- Titulo
- Descripcion
- Estado
- Monto total

Estados minimos:

- draft
- sent
- approved
- rejected

Acciones:

- Crear
- Editar
- Eliminar
- Cambiar estado
- Listar

---

# 15. Usuarios

Cada usuario debe tener:

- UUID publico
- Username
- Nombre completo
- Email
- Password encriptado
- Estado
- Roles
- Cliente asociado opcional

Estados minimos:

- active
- invited
- disabled

Acciones:

- Crear
- Editar
- Desactivar
- Listar
- Asignar roles

---

# 16. Roles y Permisos

El sistema debe trabajar con RBAC.

Roles iniciales:

- ADMIN
- EDITOR
- CLIENT

Permisos minimos:

- `users.read`
- `users.write`
- `roles.read`
- `roles.write`
- `leads.read`
- `leads.write`
- `customers.read`
- `customers.write`
- `projects.read`
- `projects.write`
- `quotes.read`
- `quotes.write`
- `blog.read`
- `blog.write`
- `services.read`
- `services.write`
- `portfolio.read`
- `portfolio.write`
- `dashboard.read`
- `portal.read.own`

Reglas:

- Un usuario puede tener multiples roles
- Un rol agrupa multiples permisos
- CLIENT solo puede ver recursos propios del portal

---

# 17. Portal del Cliente

El portal debe permitir al cliente autenticado:

- Ver su perfil
- Ver sus proyectos
- Ver sus cotizaciones
- Ver estados sin acceder a recursos ajenos

Reglas:

- Solo puede ver informacion asociada a su cliente
- Debe acceder con autenticacion JWT
- No comparte UI ni permisos del panel admin

---

# 18. Requerimientos no funcionales

- Arquitectura monolito modular
- API REST bajo `/api`
- Seguridad JWT stateless
- PostgreSQL como base principal
- Flyway para migraciones
- UUID publico en entidades de dominio
- Docker Compose para desarrollo y despliegue base
- Validaciones de lint, build y test obligatorias antes de cierre tecnico

---

# Estado del documento

Version: `2.0`

Cobertura:

- Requerimientos funcionales minimos de v1 definidos para implementacion
