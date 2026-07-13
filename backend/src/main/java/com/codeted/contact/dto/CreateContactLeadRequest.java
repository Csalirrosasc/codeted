package com.codeted.contact.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CreateContactLeadRequest(
        @NotBlank(message = "El nombre es obligatorio")
        String fullName,
        @Email(message = "Correo invalido")
        @NotBlank(message = "El correo es obligatorio")
        String email,
        String phone,
        String company,
        @NotBlank(message = "El mensaje es obligatorio")
        String message,
        @NotBlank(message = "El origen es obligatorio")
        String source
) {
}
