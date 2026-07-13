package com.codeted.auth.dto;

import java.util.UUID;

public record PermissionResponse(
        UUID publicId,
        String code,
        String module,
        String description
) {
}
