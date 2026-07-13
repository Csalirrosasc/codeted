package com.codeted.servicecatalog.dto;

import jakarta.validation.constraints.NotBlank;

public record UpdateServiceOfferingRequest(
        @NotBlank(message = "El titulo es obligatorio")
        String title,
        @NotBlank(message = "El slug es obligatorio")
        String slug,
        @NotBlank(message = "El resumen es obligatorio")
        String summary,
        String description,
        boolean featured,
        boolean active
) {
}
