package com.codeted.crm.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateCustomerRequest(
        @NotBlank(message = "El nombre es obligatorio")
        @Size(max = 120, message = "El nombre es demasiado largo")
        String fullName,
        String email,
        String phone,
        String company,
        @NotBlank(message = "El estado es obligatorio")
        String status,
        String notes
) {
}
