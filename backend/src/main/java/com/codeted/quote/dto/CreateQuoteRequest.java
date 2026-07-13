package com.codeted.quote.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record CreateQuoteRequest(
        @NotNull(message = "El cliente es obligatorio")
        UUID customerPublicId,
        @NotBlank(message = "El titulo es obligatorio")
        String title,
        String description,
        @NotBlank(message = "El estado es obligatorio")
        String status,
        @NotNull(message = "El monto es obligatorio")
        @DecimalMin(value = "0.0", inclusive = true, message = "El monto no puede ser negativo")
        BigDecimal totalAmount
) {
}
