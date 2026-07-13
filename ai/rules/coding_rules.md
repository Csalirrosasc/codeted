# Reglas de Codificación — CodeTED

> **Prioridad**: Este archivo prevalece sobre cualquier otro documento del proyecto.
> Fuentes: `.ai/README.md`, `docs/00-README.md`, `docs/01-PROJECT_VISION.md`

---

## 1. Stack Oficial

No instalar ni utilizar dependencias fuera de este stack sin aprobación explícita del equipo.

### Frontend
| Tecnología | Versión mínima |
|---|---|
| Next.js | 15.x |
| React | 19.x |
| TypeScript | 5.x (strict: true) |
| Tailwind CSS | 4.x |
| shadcn/ui | ✅ |
| Framer Motion | ✅ |
| React Hook Form | ✅ |
| Zod | ✅ |
| TanStack Query | 5.x |
| Axios | ✅ |
| Lucide React | ✅ |

### Backend
| Tecnología | Versión mínima |
|---|---|
| Java | 21 |
| Spring Boot | 3.5.x |
| Spring Security | incluido |
| JWT (jjwt) | 0.12.x |
| PostgreSQL | 16.x |
| Flyway | incluido |
| Lombok | ✅ |
| MapStruct | 1.6.x |
| Springdoc OpenAPI | 2.x |
| JUnit 5 + Mockito | incluido |

### Infraestructura
- Docker + Docker Compose
- GitHub Actions
- Vercel (frontend)
- Render (backend)
- Cloudflare

---

## 2. Arquitectura

### Patrón general
- **Monolito Modular** — Sin microservicios en la v1.
- **Clean Architecture** con **Vertical Slice** por módulo.
- **DDD adaptado** — cada módulo es su propio dominio.

### Reglas estrictas del backend
```
Controller → Service → Repository
```
- **NUNCA** acceder al Repository directamente desde el Controller.
- **NUNCA** acceder a la Base de Datos directamente desde el Controller.
- Toda comunicación entre módulos debe ser mediante interfaces de servicio.
- Usar `ApiResponse<T>` para **todas** las respuestas REST.
- Usar `GlobalExceptionHandler` — los controllers no atrapan excepciones.
- Todas las entidades extienden `BaseEntity`.

### Estructura de paquetes backend
```
com.codeted/
├── auth/           # Autenticación y autorización JWT
├── blog/           # Módulo blog (futuro)
├── common/         # Clases base: BaseEntity, ApiResponse, ErrorResponse, GlobalExceptionHandler
├── config/         # Configuraciones Spring (OpenAPI, etc.)
├── contact/        # Formulario de contacto (futuro)
├── crm/            # CRM interno (futuro)
├── portfolio/      # Portafolio (futuro)
├── project/        # Gestión de proyectos (futuro)
├── quote/          # Cotizaciones (futuro)
└── security/       # SecurityConfig, JWT filter (futuro)
```

Cada módulo con funcionalidad debe seguir esta estructura interna:
```
modulo/
├── controller/     # @RestController — solo recibe y delega
├── service/        # Lógica de negocio
├── repository/     # @Repository / JpaRepository
├── entity/         # @Entity — extiende BaseEntity
└── dto/            # Request/Response DTOs
```

### Estructura de carpetas frontend
```
frontend/
├── app/            # Next.js App Router — rutas y layouts
├── components/     # Componentes reutilizables (ui/, layout/, shared/)
├── features/       # Módulos de negocio (vertical slice)
├── hooks/          # Custom hooks
├── lib/            # Utilidades (api-client, utils)
├── services/       # Llamadas a la API agrupadas por dominio
├── types/          # Tipos globales TypeScript
└── styles/         # CSS global
```

---

## 3. Convenciones de Código

### Idioma
- **Código (variables, funciones, clases, comentarios JSDoc/Javadoc)**: Inglés
- **Interfaz de usuario (textos, labels, mensajes)**: Español
- **Mensajes de error del backend**: Español

### Commits — Conventional Commits
```
feat(auth): add JWT refresh token endpoint
fix(landing): correct hero section mobile layout
docs(readme): update setup instructions
chore(deps): bump spring-boot to 3.5.1
```

### Branches — Git Flow
```
main          → producción
develop       → integración
feature/...   → nuevas funcionalidades
fix/...       → correcciones
release/...   → preparación de release
hotfix/...    → parches urgentes en main
```

### TypeScript
- `strict: true` siempre activado.
- No usar `any` — usar `unknown` y hacer type narrowing.
- Preferir `interface` sobre `type` para objetos, `type` para uniones/intersecciones.
- Exportar tipos desde `types/index.ts`.

### Java
- Usar `@Data`, `@Getter`, `@Setter`, `@Builder` de Lombok correctamente.
- Usar `@Valid` en todos los request bodies del controller.
- No usar `System.out.println` — siempre `@Slf4j` + `log.info/warn/error`.
- Todos los endpoints deben estar documentados con `@Operation` de Springdoc.

---

## 4. Calidad Mínima

Todo código entregado DEBE:

- ✅ Compilar sin errores ni warnings
- ✅ Tener tipado estricto (no `any`, no tipos implícitos)
- ✅ Ser responsive (mobile-first)
- ✅ Estar documentado (JSDoc en frontend, Javadoc en backend)
- ✅ Seguir principios SOLID y DRY
- ✅ Pasar ESLint sin errores
- ✅ Pasar Prettier (formato consistente)
- ✅ No contener `TODO` sin descripción y ticket asociado
- ✅ No contener código duplicado
- ✅ Tener manejo de errores adecuado

---

## 5. Definition of Done

Una funcionalidad está **terminada** ÚNICAMENTE cuando:

1. Compila correctamente.
2. Pasa todas las pruebas (unitarias e integración).
3. No rompe funcionalidades existentes.
4. Está documentada (API docs + comentarios).
5. Es accesible (WCAG AA).
6. Es responsive (mobile, tablet, desktop).
7. Tiene manejo de errores completo.
8. Está integrada al proyecto (PR mergeado a `develop`).
9. Pasa ESLint y Prettier.
10. Pasa Lighthouse 95+ en Performance y SEO 100.

---

## 6. Reglas para Agentes IA

Cuando un agente trabaje en este proyecto:

1. **Leer primero**: `ai/rules/coding_rules.md` → `ai/context/` → `ai/templates/` → `docs/`
2. **No implementar** funcionalidades de negocio sin que estén especificadas en `docs/` o `prompts/`.
3. **No añadir dependencias** fuera del stack oficial sin reportarlo primero.
4. **No tomar decisiones arquitectónicas** no documentadas — detener y reportar.
5. **No hardcodear** credenciales, URLs o secretos — usar variables de entorno.
6. **No duplicar** código — reutilizar `BaseEntity`, `ApiResponse`, tipos globales.
7. Los mensajes de error del backend deben estar en **español**.
8. El código fuente debe estar en **inglés**.
9. Siempre usar `ApiResponse<T>` en los controllers.
10. Siempre extender `BaseEntity` en las entidades JPA.
