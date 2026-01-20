package com.e_commerce.backend_api.model;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "orders")
public class Order {

    @Id
    private String id;

    private String userId;

    private double totalAmount;

    private String status;

    @CreatedDate
    private LocalDateTime createdAt;
}
