package com.e_commerce.backend_api.service;

import com.e_commerce.backend_api.dtos.SignUpRequest;
import com.e_commerce.backend_api.model.User;
import com.e_commerce.backend_api.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User registerUser(SignUpRequest signUpRequest) {
        User user = new User();
        user.setEmail(signUpRequest.email());
        user.setUsername(signUpRequest.username());
        user.setRole(signUpRequest.role());
        return userRepository.createUser(user);
    }
}
