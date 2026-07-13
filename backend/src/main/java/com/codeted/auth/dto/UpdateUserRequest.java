package com.codeted.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.Set;
import java.util.UUID;

public record UpdateUserRequest(
        @NotBlank(message = "El nombre completo es obligatorio")
        String fullName,
        @Email(message = "Correo invalido")
        @NotBlank(message = "El correo es obligatorio")
        String email,
        @NotBlank(message = "El estado es obligatorio")
        String status,
        Set<String> roleNames,
        UUID customerPublicId
) {
}
