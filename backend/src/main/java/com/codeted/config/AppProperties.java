package com.codeted.config;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "app")
public record AppProperties(
        @Valid Security security,
        @Valid Cors cors
) {

    public record Security(
            @Valid Jwt jwt,
            @Valid RateLimit rateLimit
    ) {
    }

    public record Jwt(
            @NotBlank String secret,
            @Min(60000) long expirationMs,
            @Min(300000) long refreshExpirationMs
    ) {
    }

    public record RateLimit(
            @Min(1) int authMaxRequestsPerMinute,
            @Min(1) int contactMaxRequestsPerMinute
    ) {
    }

    public record Cors(
            @NotBlank String allowedOrigins
    ) {
    }
}
