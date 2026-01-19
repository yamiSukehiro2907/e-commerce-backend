package com.e_commerce.backend_api.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record SignUpRequest(
        @NotBlank
        String username,
        @Email
        String email,
        @NotBlank
        String role
) {
}
