package com.e_commerce.backend_api.repositories;

import com.e_commerce.backend_api.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

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

    public Product save(Product product) {
        return mongoTemplate.save(product);
    }


    public List<Product> findByName(String productName) {
        Query query = new Query();
        query.addCriteria(Criteria.where("name").regex("^" + Pattern.quote(productName) + "$", "i"));
        return mongoTemplate.find(query, Product.class);
    }
}
