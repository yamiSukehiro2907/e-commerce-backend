package com.e_commerce.backend_api.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "payments")
public class Payment {

    @Id
    private String paymentId;

    private String orderId;

    private double amount;

    private String status;

    private String transactionId;

    private LocalDateTime createdAt;
}
