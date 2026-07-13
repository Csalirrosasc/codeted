# BACKEND ARCHITECTURE

Version: 0.1

## Stack

- Java 21
- Spring Boot 3.5
- Spring Security
- Flyway
- PostgreSQL
- Springdoc OpenAPI

## Reglas

- Controller -> Service -> Repository
- DTOs para toda API pública
- `ApiResponse<T>` y `ErrorResponse`
- No exponer entidades JPA

## Estado actual

El backend cuenta con infraestructura base, seguridad inicial, documentación OpenAPI y un módulo `blog` público mínimo. Los módulos de negocio siguen pendientes.
