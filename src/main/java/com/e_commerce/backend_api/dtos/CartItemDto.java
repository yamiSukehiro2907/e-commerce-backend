package com.e_commerce.backend_api.dtos;

public record CartItemDto(
        String id,
        String productId,
        int quantity,
        ProductDto product
) {
}
