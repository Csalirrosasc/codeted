package com.codeted.servicecatalog.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateServiceOfferingRequest(
        @NotBlank(message = "El titulo es obligatorio")
        @Size(max = 140, message = "El titulo es demasiado largo")
        String title,
        @NotBlank(message = "El slug es obligatorio")
        @Size(max = 160, message = "El slug es demasiado largo")
        String slug,
        @NotBlank(message = "El resumen es obligatorio")
        @Size(max = 280, message = "El resumen es demasiado largo")
        String summary,
        String description,
        boolean featured,
        boolean active
) {
}
