package com.codeted.project.dto;

import java.time.LocalDate;
import java.util.UUID;

public record ProjectResponse(
        Long id,
        UUID publicId,
        UUID customerPublicId,
        String customerName,
        String name,
        String description,
        String status,
        LocalDate startDate,
        LocalDate endDate
) {
}
