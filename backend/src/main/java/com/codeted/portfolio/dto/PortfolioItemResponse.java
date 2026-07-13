package com.codeted.portfolio.dto;

import java.util.UUID;

public record PortfolioItemResponse(
        Long id,
        UUID publicId,
        String title,
        String slug,
        String category,
        String summary,
        String challenge,
        String solution,
        String result,
        boolean featured,
        boolean published
) {
}
