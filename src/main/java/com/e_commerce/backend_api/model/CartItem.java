package com.e_commerce.backend_api.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "cart_items")
public class CartItem {

    @Id
    private String id;

    private String userId;

    private String productId;

    private int quantity;
}
