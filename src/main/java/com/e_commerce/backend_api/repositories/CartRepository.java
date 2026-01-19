package com.e_commerce.backend_api.repositories;

import com.e_commerce.backend_api.model.CartItem;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class CartRepository {

    private final MongoTemplate mongoTemplate;

    public CartItem addToCart(CartItem cartItem) {
        return mongoTemplate.insert(cartItem);
    }

    public List<CartItem> findByUserId(String userId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("userId").is(userId));
        return mongoTemplate.find(query, CartItem.class);
    }
}
