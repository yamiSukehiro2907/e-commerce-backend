package com.e_commerce.backend_api.repositories;

import com.e_commerce.backend_api.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ProductRepository {

    private final MongoTemplate mongoTemplate;

    public Optional<Product> create(Product product) {
        return Optional.of(mongoTemplate.insert(product));
    }

    public List<Product> getAllProducts() {
        return mongoTemplate.findAll(Product.class);
    }

    public Product findById(String productId) {
        return mongoTemplate.findById(productId, Product.class);
    }

}
