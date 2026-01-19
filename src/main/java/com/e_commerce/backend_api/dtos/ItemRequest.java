package com.e_commerce.backend_api.dtos;

import jakarta.validation.constraints.NotBlank;

public record ItemRequest(
        @NotBlank
        String userId,
        @NotBlank
        String productId,
        @NotBlank
        int quantity
) {
}
