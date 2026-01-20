package com.e_commerce.backend_api.repositories;

import com.e_commerce.backend_api.model.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PaymentRepository {

    private final MongoTemplate mongoTemplate;

    public Payment createPayment(Payment payment) {
        return mongoTemplate.insert(payment);
    }

}
