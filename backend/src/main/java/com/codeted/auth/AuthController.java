package com.codeted.auth;

import com.codeted.auth.dto.AuthResponse;
import com.codeted.auth.dto.LoginRequest;
import com.codeted.auth.dto.RefreshTokenRequest;
import com.codeted.auth.dto.UserProfileResponse;
import com.codeted.auth.service.AuthService;
import com.codeted.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "Login", description = "Authenticates a user and returns access and refresh tokens.")
    @PostMapping("/login")
    public ApiResponse<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        return ApiResponse.success("Inicio de sesión exitoso", authService.login(request));
    }

    @Operation(summary = "Refresh access token", description = "Issues a new access token from a valid refresh token.")
    @PostMapping("/refresh")
    public ApiResponse<AuthResponse> refresh(@Valid @RequestBody RefreshTokenRequest request) {
        return ApiResponse.success("Token renovado correctamente", authService.refresh(request));
    }

    @Operation(summary = "Get current user profile", description = "Returns the authenticated user profile.")
    @GetMapping("/me")
    public ApiResponse<UserProfileResponse> me() {
        return ApiResponse.success("Perfil obtenido correctamente", authService.getCurrentUserProfile());
    }

    @Operation(summary = "Logout", description = "Revokes the provided refresh token.")
    @PostMapping("/logout")
    public ApiResponse<Void> logout(@Valid @RequestBody RefreshTokenRequest request) {
        authService.logout(request);
        return ApiResponse.success("Sesión cerrada correctamente");
    }
}
