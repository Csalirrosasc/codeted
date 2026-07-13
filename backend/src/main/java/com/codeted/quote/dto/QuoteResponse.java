package com.codeted.quote.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record QuoteResponse(
        Long id,
        UUID publicId,
        UUID customerPublicId,
        String customerName,
        String title,
        String description,
        String status,
        BigDecimal totalAmount
) {
}
