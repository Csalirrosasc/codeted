package com.codeted.contact.dto;

import java.util.UUID;

public record ContactLeadResponse(
        Long id,
        UUID publicId,
        String fullName,
        String email,
        String phone,
        String company,
        String message,
        String source,
        String status
) {
}
