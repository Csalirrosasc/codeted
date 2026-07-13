package com.codeted.contact.dto;

import jakarta.validation.constraints.NotBlank;

public record UpdateContactLeadStatusRequest(
        @NotBlank(message = "El estado es obligatorio")
        String status
) {
}
