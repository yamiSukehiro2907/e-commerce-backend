package com.e_commerce.backend_api.controller;

import com.e_commerce.backend_api.dtos.PaymentRequest;
import com.e_commerce.backend_api.dtos.PaymentDto;
import com.e_commerce.backend_api.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping
    public ResponseEntity<?> getAllPayments() {
        return new ResponseEntity<>(paymentService.getAllPayments(), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createPayment(@RequestBody PaymentRequest paymentRequest) {
        return paymentService.createPayment(paymentRequest);
    }

    @PostMapping("/pay/{paymentId}")
    public ResponseEntity<?> updatePayment(@PathVariable String paymentId) {
        return paymentService.processPayment(paymentId);
    }
}
