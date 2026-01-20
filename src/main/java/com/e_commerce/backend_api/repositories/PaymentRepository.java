package com.e_commerce.backend_api.repositories;

import com.e_commerce.backend_api.model.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PaymentRepository {

    private final MongoTemplate mongoTemplate;

    public Payment createPayment(Payment payment) {
        return mongoTemplate.insert(payment);
    }

    public Payment findById(String id) {
        return  mongoTemplate.findById(id, Payment.class);
    }

    public Payment save(Payment payment) {
        return mongoTemplate.save(payment);
    }

    public List<Payment> findAll(){
        return  mongoTemplate.findAll(Payment.class);
    }

}
