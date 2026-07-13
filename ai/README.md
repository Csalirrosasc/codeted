# CodeTED AI Workspace

## Objetivo

La carpeta `ai/` contiene toda la información necesaria para que cualquier agente de codificación (Codex, Cursor, Claude Code, Cline, Gemini CLI, Windsurf, etc.) pueda trabajar sobre este proyecto sin romper la arquitectura ni la calidad del código.

La documentación de la carpeta `docs/` explica el negocio.

La carpeta `ai/` explica **cómo debe trabajar el agente**.

---

# Prioridad

Siempre seguir este orden.

1. rules/
2. context/
3. templates/
4. prompts/
5. docs/

Si existe conflicto entre documentos, siempre prevalecerán las reglas de la carpeta `rules`.

---

# Stack Oficial

Frontend

- Next.js 15
- React 19
- TypeScript
- Tailwind CSS
- shadcn/ui
- Framer Motion
- React Hook Form
- Zod
- TanStack Query
- Axios

Backend

- Java 21
- Spring Boot 3.5
- Spring Security
- PostgreSQL
- Flyway
- MapStruct
- Lombok
- Swagger
- JWT

Infraestructura

- Docker
- Docker Compose
- GitHub Actions
- Vercel
- Render
- Cloudflare

---

# Arquitectura

El proyecto sigue un Monolito Modular.

No se implementarán microservicios durante la primera versión.

Se utilizará Clean Architecture.

Los módulos deberán ser independientes.

Toda comunicación será mediante servicios.

Nunca acceder directamente al repositorio desde el Controller.

Nunca acceder directamente a la Base de Datos desde el Controller.

---

# Convenciones

Idioma del código

Inglés

Idioma de la interfaz

Español

Commits

Conventional Commits

Branches

Git Flow

---

# Calidad mínima

Todo código deberá:

- Compilar.
- Tener tipado estricto.
- Ser responsive.
- Estar documentado.
- Seguir SOLID.
- Seguir DRY.
- Pasar ESLint.
- Pasar Prettier.
- No tener código duplicado.
- No contener TODO sin descripción.

---

# Definition of Done

Una tarea estará terminada únicamente cuando:

- Compile correctamente.
- Pase las pruebas.
- No rompa funcionalidades existentes.
- Esté documentada.
- Sea accesible.
- Sea responsive.
- Tenga manejo de errores.
- Esté integrada al proyecto.

---

# Archivos Importantes

docs/

Documentación funcional.

ai/

Reglas para IA.

frontend/

Aplicación Frontend.

backend/

API REST.

database/

Migraciones.

docker/

Infraestructura.

prompts/

Prompts para implementación.
