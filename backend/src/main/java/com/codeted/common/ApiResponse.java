package com.codeted.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.time.Instant;

/**
 * Standard API response wrapper for all REST endpoints.
 *
 * <p>Every controller response must be wrapped in this class to ensure
 * a consistent contract with frontend consumers.
 *
 * <p>Example success response:
 * <pre>{@code
 * {
 *   "success": true,
 *   "message": "Operación exitosa",
 *   "data": { ... },
 *   "timestamp": "2026-07-13T19:00:00Z"
 * }
 * }</pre>
 *
 * @param <T> the type of the response payload
 */
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

    private final boolean success;
    private final String message;
    private final T data;
    private final String timestamp;

    private ApiResponse(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.timestamp = Instant.now().toString();
    }

    /**
     * Creates a successful response with data payload.
     *
     * @param message human-readable success message
     * @param data    the response payload
     * @param <T>     type of the payload
     * @return wrapped success response
     */
    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(true, message, data);
    }

    /**
     * Creates a successful response without a data payload.
     *
     * @param message human-readable success message
     * @return wrapped success response
     */
    public static <T> ApiResponse<T> success(String message) {
        return new ApiResponse<>(true, message, null);
    }

    /**
     * Creates an error response.
     *
     * @param message human-readable error message
     * @return wrapped error response
     */
    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(false, message, null);
    }
}
