package com.codeted.crm.dto;

import java.util.UUID;

public record CustomerResponse(
        Long id,
        UUID publicId,
        String fullName,
        String email,
        String phone,
        String company,
        String status,
        String notes
) {
}
