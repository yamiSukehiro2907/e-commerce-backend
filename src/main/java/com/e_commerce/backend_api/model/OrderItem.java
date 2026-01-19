package com.e_commerce.backend_api.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "order_items")
@Data
public class OrderItem {

    @Id
    private String id;

    private String orderId;

    private String productId;

    private int quantity;

    private double price;
}
