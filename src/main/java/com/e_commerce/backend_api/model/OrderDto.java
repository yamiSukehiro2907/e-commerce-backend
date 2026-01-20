package com.e_commerce.backend_api.model;

import java.util.List;

public record OrderDto(
        String id,
        String userId,
        double totalAmount,
        String status,
        List<OrderItemDto> items
) {

}
