package com.codeted.auth;

import com.codeted.auth.dto.CreateUserRequest;
import com.codeted.auth.dto.UserSummaryResponse;
import com.codeted.auth.dto.UpdateUserRequest;
import com.codeted.auth.service.UserManagementService;
import com.codeted.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserManagementController {

    private final UserManagementService userManagementService;

    @GetMapping
    @PreAuthorize("hasAuthority('users.read')")
    @Operation(summary = "List users", description = "Returns administrative users and client users.")
    public ApiResponse<List<UserSummaryResponse>> findAll() {
        return ApiResponse.success("Usuarios obtenidos correctamente", userManagementService.findAll());
    }

    @PostMapping
    @PreAuthorize("hasAuthority('users.write')")
    @Operation(summary = "Create user", description = "Creates a new user with role assignments and optional client link.")
    public ApiResponse<UserSummaryResponse> create(@Valid @RequestBody CreateUserRequest request) {
        return ApiResponse.success("Usuario creado correctamente", userManagementService.create(request));
    }

    @PatchMapping("/{publicId}")
    @PreAuthorize("hasAuthority('users.write')")
    @Operation(summary = "Update user", description = "Updates profile, status, roles and client link.")
    public ApiResponse<UserSummaryResponse> update(
            @PathVariable UUID publicId,
            @Valid @RequestBody UpdateUserRequest request
    ) {
        return ApiResponse.success("Usuario actualizado correctamente", userManagementService.update(publicId, request));
    }
}
