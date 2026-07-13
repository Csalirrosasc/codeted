package com.codeted.blog.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateBlogPostRequest(
        @NotBlank(message = "El titulo es obligatorio")
        @Size(max = 160, message = "El titulo es demasiado largo")
        String title,
        @NotBlank(message = "El slug es obligatorio")
        @Size(max = 180, message = "El slug es demasiado largo")
        String slug,
        @NotBlank(message = "El autor es obligatorio")
        @Size(max = 100, message = "El autor es demasiado largo")
        String author,
        @NotBlank(message = "El extracto es obligatorio")
        @Size(max = 320, message = "El extracto es demasiado largo")
        String excerpt,
        String content,
        boolean featured,
        boolean published
) {
}
