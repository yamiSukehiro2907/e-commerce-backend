package com.e_commerce.backend_api.dtos;

public record UpdatePaymentRequest(
        String paymentId,
        String status,
        String transactionId
) {
}
