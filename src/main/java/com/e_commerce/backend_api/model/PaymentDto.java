package com.e_commerce.backend_api.model;

public record PaymentDto(
        String paymentId,
        String orderId,
        double amount,
        String status
) {
}
