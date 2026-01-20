package com.e_commerce.backend_api.repositories;

import com.e_commerce.backend_api.model.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final MongoTemplate mongoTemplate;

    public Order create(Order order) {
        return mongoTemplate.insert(order);
    }

    public Order findById(String id) {
        return mongoTemplate.findById(id, Order.class);
    }

    public List<Order> findByUserId(String userId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("userId").is(userId));
        return mongoTemplate.find(query, Order.class);
    }

    public Order save(Order order) {
        return mongoTemplate.save(order);
    }

    public Order findByPaymentId(String paymentId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("paymentId").is(paymentId));
        return mongoTemplate.findOne(query, Order.class);
    }
}
