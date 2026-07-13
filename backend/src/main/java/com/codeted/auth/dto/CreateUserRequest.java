package com.codeted.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.Set;
import java.util.UUID;

public record CreateUserRequest(
        @NotBlank(message = "El username es obligatorio")
        String username,
        @NotBlank(message = "El nombre completo es obligatorio")
        String fullName,
        @Email(message = "Correo invalido")
        @NotBlank(message = "El correo es obligatorio")
        String email,
        @NotBlank(message = "La clave es obligatoria")
        String password,
        @NotBlank(message = "El estado es obligatorio")
        String status,
        Set<String> roleNames,
        UUID customerPublicId
) {
}
