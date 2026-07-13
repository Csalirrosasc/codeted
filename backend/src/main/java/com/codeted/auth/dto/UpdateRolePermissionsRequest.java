package com.codeted.auth.dto;

import jakarta.validation.constraints.NotEmpty;

import java.util.Set;

public record UpdateRolePermissionsRequest(
        @NotEmpty(message = "Debes enviar al menos un permiso")
        Set<String> permissionCodes
) {
}
