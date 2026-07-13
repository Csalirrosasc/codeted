package com.codeted.auth.dto;

import java.util.Set;
import java.util.UUID;

public record UserProfileResponse(
        Long id,
        UUID publicId,
        String username,
        String fullName,
        String email,
        String role,
        String status,
        Set<String> roles,
        UUID customerPublicId
) {
}
