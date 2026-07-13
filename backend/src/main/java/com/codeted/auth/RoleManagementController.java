package com.codeted.auth;

import com.codeted.auth.dto.RoleResponse;
import com.codeted.auth.dto.UpdateRolePermissionsRequest;
import com.codeted.auth.service.RoleManagementService;
import com.codeted.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
public class RoleManagementController {

    private final RoleManagementService roleManagementService;

    @GetMapping
    @PreAuthorize("hasAuthority('roles.read')")
    @Operation(summary = "List roles", description = "Returns roles with their permission sets.")
    public ApiResponse<List<RoleResponse>> findAll() {
        return ApiResponse.success("Roles obtenidos correctamente", roleManagementService.findAll());
    }

    @PatchMapping("/{publicId}/permissions")
    @PreAuthorize("hasAuthority('roles.write')")
    @Operation(summary = "Update role permissions", description = "Updates the permissions assigned to a role.")
    public ApiResponse<RoleResponse> updatePermissions(
            @PathVariable UUID publicId,
            @Valid @RequestBody UpdateRolePermissionsRequest request
    ) {
        return ApiResponse.success("Permisos del rol actualizados correctamente", roleManagementService.updatePermissions(publicId, request));
    }
}
