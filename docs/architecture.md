# Codeted Architecture and Stack Documentation

## Tech Stack Overview

### Frontend
- **Framework:** Next.js 15 (App Router, React 19)
- **Styling:** Tailwind CSS (v4)
- **UI Kit:** shadcn/ui
- **Animations:** Framer Motion
- **Form Management:** React Hook Form + Zod validation
- **State & Data Fetching:** TanStack Query + Axios

### Backend
- **Core:** Java 21 & Spring Boot 3.5.0 (stable parent)
- **Security:** Spring Security + JWT Stateless Authentication
- **ORM & Database:** Spring Data JPA + Hibernate (PostgreSQL driver)
- **Migrations:** Flyway Database migrations
- **Documentation:** SpringDoc OpenAPI v3 (Swagger)
- **Boilerplate Reduction:** Lombok & MapStruct

---

## Folder Layout

```
codeted/
├── frontend/                     # Next.js 15 App
│   ├── app/                      # Pages and app router entries
│   ├── components/               # Shareable components (shadcn/ui elements here)
│   ├── features/                 # Modular, feature-specific parts
│   ├── hooks/                    # Custom react hooks
│   ├── lib/                      # Helper libraries & utils
│   ├── services/                 # API client utilities (Axios instance, Query providers)
│   ├── types/                    # TypeScript type declarations
│   └── styles/                   # Stylesheets and custom variables
│
├── backend/                      # Spring Boot App
│   ├── src/main/java/com/codeted/
│   │   ├── auth/                 # Authentication controller & endpoints
│   │   ├── common/               # Shared logic & custom exceptions
│   │   ├── config/               # Spring Beans configuration
│   │   ├── crm/                  # CRM module
│   │   ├── portfolio/            # Portfolio module
│   │   ├── blog/                 # Blog module
│   │   ├── contact/              # Contact forms & actions
│   │   ├── quote/                # Client quote management
│   │   ├── project/              # Project and task workflow
│   │   └── security/             # Security filters, JWT providers, etc.
│   └── src/main/resources/
│       ├── db/migration/         # Flyway SQL migrations
│       └── application.yml       # Active configurations
│
├── database/                     # Custom database scripts and schemas
├── docker/                       # Dedicated Docker configurations
└── docs/                         # General architectural documentation
```

---

## Development Guide

### Running Database
Use the root Docker Compose config to spin up PostgreSQL:
```bash
docker compose up -d
```

### Running Backend
```bash
cd backend
./mvnw spring-boot:run
```
Swagger UI will be available at: `http://localhost:8080/api/swagger-ui.html`
API documentation JSON is available at: `http://localhost:8080/api/v3/api-docs`

### Running Frontend
```bash
cd frontend
npm run dev
```
The site will run on `http://localhost:3000`.
