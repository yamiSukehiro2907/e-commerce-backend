package com.e_commerce.backend_api.model;

public record OrderItemDto(
        String productId,
        int quantity,
        double price
) {
}
