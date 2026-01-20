package com.e_commerce.backend_api.dtos;

public record OrderItemDto(
        String productId,
        int quantity,
        double price
) {
}
