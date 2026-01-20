package com.e_commerce.backend_api.repositories;

import com.e_commerce.backend_api.model.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
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
        return mongoTemplate.findById(id, Payment.class);
    }

    public Payment save(Payment payment) {
        return mongoTemplate.save(payment);
    }

    public List<Payment> findAll() {
        return mongoTemplate.findAll(Payment.class);
    }

    public List<Payment> findByOrderId(String orderId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("orderId").is(orderId));
        return mongoTemplate.find(query, Payment.class);
    }

}
