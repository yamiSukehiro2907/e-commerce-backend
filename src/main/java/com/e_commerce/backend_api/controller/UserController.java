package com.e_commerce.backend_api.controller;

import com.e_commerce.backend_api.dtos.SignUpRequest;
import com.e_commerce.backend_api.model.User;
import com.e_commerce.backend_api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody SignUpRequest signUpRequest) {
        User user = userService.registerUser(signUpRequest);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }
}
