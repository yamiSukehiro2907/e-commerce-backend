package com.e_commerce.backend_api.repositories;

import com.e_commerce.backend_api.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final MongoTemplate mongoTemplate;

    public User createUser(User user) {
        return mongoTemplate.insert(user);
    }

    public User findById(String userId) {
        return mongoTemplate.findById(userId, User.class);
    }
}
