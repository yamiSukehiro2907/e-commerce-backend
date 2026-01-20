package com.e_commerce.backend_api.dtos;

public record WebHookResponse(
        String transactionId,
        String status
) {
}
