package com.codeted.servicecatalog.dto;

import java.util.UUID;

public record ServiceOfferingResponse(
        Long id,
        UUID publicId,
        String title,
        String slug,
        String summary,
        String description,
        boolean featured,
        boolean active
) {
}
