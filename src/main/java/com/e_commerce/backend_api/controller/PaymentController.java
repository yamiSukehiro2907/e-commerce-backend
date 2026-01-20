package com.e_commerce.backend_api.controller;

import com.e_commerce.backend_api.dtos.PaymentRequest;
import com.e_commerce.backend_api.model.PaymentDto;
import com.e_commerce.backend_api.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/create")
    public ResponseEntity<?> createPayment(@RequestBody PaymentRequest paymentRequest) {
        PaymentDto paymentDto = paymentService.createPayment(paymentRequest);
        if (paymentDto == null) return ResponseEntity.badRequest().body("Payment not created");
        return new ResponseEntity<>(paymentDto, HttpStatus.CREATED);
    }
}
