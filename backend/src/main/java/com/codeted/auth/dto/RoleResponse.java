package com.codeted.auth.dto;

import java.util.Set;
import java.util.UUID;

public record RoleResponse(
        UUID publicId,
        String name,
        String description,
        boolean systemRole,
        Set<PermissionResponse> permissions
) {
}
