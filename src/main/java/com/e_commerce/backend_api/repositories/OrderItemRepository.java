package com.e_commerce.backend_api.repositories;

import com.e_commerce.backend_api.model.OrderItem;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderItemRepository {

    private final MongoTemplate mongoTemplate;

    public OrderItem save(OrderItem orderItem) {
        return mongoTemplate.save(orderItem);
    }

    public List<OrderItem> findByOrderId(String orderId) {
        Query query = new Query(Criteria.where("orderId").is(orderId));
        return mongoTemplate.find(query, OrderItem.class);
    }
}
