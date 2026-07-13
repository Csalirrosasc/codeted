package com.codeted.common;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * Centralized exception handler for all REST controllers.
 *
 * <p>This class intercepts exceptions thrown within any {@code @RestController}
 * and translates them into consistent {@link ErrorResponse} payloads.
 *
 * <p>Architecture note: Controllers must NOT catch exceptions locally.
 * Let them propagate to this handler to maintain a single error-handling strategy.
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // ------------------------------------------------------------------
    // Validation Errors (400)
    // ------------------------------------------------------------------

    /**
     * Handles Bean Validation errors from {@code @Valid} annotated request bodies.
     * Returns a 400 with a map of field → error message.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String field = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(field, message);
        });

        log.warn("Validation error: {}", errors);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse("Error de validación", HttpStatus.BAD_REQUEST.value(), errors));
    }

    /**
     * Handles constraint violations from path/query parameters.
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolation(ConstraintViolationException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getConstraintViolations().forEach(violation -> {
            String field = violation.getPropertyPath().toString();
            errors.put(field, violation.getMessage());
        });

        log.warn("Constraint violation: {}", errors);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse("Error de validación", HttpStatus.BAD_REQUEST.value(), errors));
    }

    // ------------------------------------------------------------------
    // Not Found (404)
    // ------------------------------------------------------------------

    /**
     * Handles JPA entity not found errors (404).
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFound(EntityNotFoundException ex) {
        log.warn("Entity not found: {}", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND.value()));
    }

    // ------------------------------------------------------------------
    // Authorization Errors (401 / 403)
    // ------------------------------------------------------------------

    /**
     * Handles unauthenticated requests (401).
     */
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleAuthenticationException(AuthenticationException ex) {
        log.warn("Authentication failure: {}", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorResponse("No autenticado. Por favor inicia sesión.", HttpStatus.UNAUTHORIZED.value()));
    }

    /**
     * Handles access denied errors (403).
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDenied(AccessDeniedException ex) {
        log.warn("Access denied: {}", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(new ErrorResponse("No tienes permisos para realizar esta acción.", HttpStatus.FORBIDDEN.value()));
    }

    // ------------------------------------------------------------------
    // Generic Fallback (500)
    // ------------------------------------------------------------------

    /**
     * Catches any unhandled exception and returns a 500 without leaking internal details.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        log.error("Unhandled exception", ex);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse(
                        "Ha ocurrido un error interno. Por favor inténtalo de nuevo más tarde.",
                        HttpStatus.INTERNAL_SERVER_ERROR.value()
                ));
    }
}
