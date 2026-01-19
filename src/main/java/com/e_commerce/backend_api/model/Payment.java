package com.e_commerce.backend_api.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "payments")
public class Payment {

    private String id;

    private String orderId;

    private double amount;

    private String status;

    private String paymentId;

    private LocalDateTime createdAt;
}
