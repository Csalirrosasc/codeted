package com.codeted.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.time.Instant;
import java.util.Map;

/**
 * Standard error response DTO for the CodeTED REST API.
 *
 * <p>This class is used exclusively by {@link GlobalExceptionHandler} to
 * produce consistent error payloads across all endpoints.
 *
 * <p>Field-level validation errors are included in the {@code errors} map
 * when applicable (e.g., Bean Validation failures).
 *
 * <p>Example error response:
 * <pre>{@code
 * {
 *   "success": false,
 *   "message": "Error de validación",
 *   "status": 400,
 *   "errors": {
 *     "email": "El email no es válido"
 *   },
 *   "timestamp": "2026-07-13T19:00:00Z"
 * }
 * }</pre>
 */
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {

    private final boolean success = false;
    private final String message;
    private final int status;
    private final Map<String, String> errors;
    private final String timestamp;

    /**
     * Creates an error response without field-level errors.
     *
     * @param message human-readable error description
     * @param status  HTTP status code
     */
    public ErrorResponse(String message, int status) {
        this(message, status, null);
    }

    /**
     * Creates an error response with field-level validation errors.
     *
     * @param message human-readable error description
     * @param status  HTTP status code
     * @param errors  map of field name → error message
     */
    public ErrorResponse(String message, int status, Map<String, String> errors) {
        this.message = message;
        this.status = status;
        this.errors = errors;
        this.timestamp = Instant.now().toString();
    }
}
