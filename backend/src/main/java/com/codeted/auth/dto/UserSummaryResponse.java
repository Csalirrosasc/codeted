package com.codeted.auth.dto;

import java.util.Set;
import java.util.UUID;

public record UserSummaryResponse(
        UUID publicId,
        String username,
        String fullName,
        String email,
        String status,
        String primaryRole,
        Set<String> roles,
        UUID customerPublicId
) {
}
