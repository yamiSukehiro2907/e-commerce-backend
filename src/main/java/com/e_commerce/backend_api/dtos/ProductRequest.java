package com.e_commerce.backend_api.dtos;

import jakarta.validation.constraints.NotBlank;

public record ProductRequest(
        @NotBlank
        String name,
        String description,
        @NotBlank
        double price,
        @NotBlank
        int stock
) {
}
