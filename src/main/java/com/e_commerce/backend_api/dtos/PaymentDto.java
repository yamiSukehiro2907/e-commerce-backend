package com.e_commerce.backend_api.dtos;

public record PaymentDto(
        String paymentId,
        String orderId,
        double amount,
        String status,
        String transactionId
) {
}
