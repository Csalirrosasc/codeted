package com.codeted.portfolio.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreatePortfolioItemRequest(
        @NotBlank(message = "El titulo es obligatorio")
        @Size(max = 150, message = "El titulo es demasiado largo")
        String title,
        @NotBlank(message = "El slug es obligatorio")
        @Size(max = 170, message = "El slug es demasiado largo")
        String slug,
        @NotBlank(message = "La categoria es obligatoria")
        @Size(max = 80, message = "La categoria es demasiado larga")
        String category,
        @NotBlank(message = "El resumen es obligatorio")
        @Size(max = 280, message = "El resumen es demasiado largo")
        String summary,
        String challenge,
        String solution,
        String result,
        boolean featured,
        boolean published
) {
}
