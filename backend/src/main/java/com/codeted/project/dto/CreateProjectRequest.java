package com.codeted.project.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public record CreateProjectRequest(
        @NotNull(message = "El cliente es obligatorio")
        UUID customerPublicId,
        @NotBlank(message = "El nombre es obligatorio")
        String name,
        String description,
        @NotBlank(message = "El estado es obligatorio")
        String status,
        LocalDate startDate,
        LocalDate endDate
) {
}
