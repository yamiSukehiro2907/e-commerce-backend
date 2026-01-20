package com.e_commerce.backend_api.dtos;

public record PaymentRequest(
        String orderId,
        double amount
) {
}
