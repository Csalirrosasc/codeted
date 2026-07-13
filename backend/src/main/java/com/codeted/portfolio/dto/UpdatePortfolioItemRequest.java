package com.codeted.portfolio.dto;

import jakarta.validation.constraints.NotBlank;

public record UpdatePortfolioItemRequest(
        @NotBlank(message = "El titulo es obligatorio")
        String title,
        @NotBlank(message = "El slug es obligatorio")
        String slug,
        @NotBlank(message = "La categoria es obligatoria")
        String category,
        @NotBlank(message = "El resumen es obligatorio")
        String summary,
        String challenge,
        String solution,
        String result,
        boolean featured,
        boolean published
) {
}
