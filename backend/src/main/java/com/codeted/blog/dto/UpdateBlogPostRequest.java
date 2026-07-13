package com.codeted.blog.dto;

import jakarta.validation.constraints.NotBlank;

public record UpdateBlogPostRequest(
        @NotBlank(message = "El titulo es obligatorio")
        String title,
        @NotBlank(message = "El slug es obligatorio")
        String slug,
        @NotBlank(message = "El autor es obligatorio")
        String author,
        @NotBlank(message = "El extracto es obligatorio")
        String excerpt,
        String content,
        boolean featured,
        boolean published
) {
}
