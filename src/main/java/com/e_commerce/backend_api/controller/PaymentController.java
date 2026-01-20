package com.e_commerce.backend_api.controller;

import com.e_commerce.backend_api.dtos.PaymentRequest;
import com.e_commerce.backend_api.dtos.UpdatePaymentRequest;
import com.e_commerce.backend_api.model.PaymentDto;
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
        PaymentDto paymentDto = paymentService.createPayment(paymentRequest);
        if (paymentDto == null) return ResponseEntity.badRequest().body("Payment not created");
        return new ResponseEntity<>(paymentDto, HttpStatus.CREATED);
    }

    @PostMapping("/update")
    public ResponseEntity<?> updatePayment(@RequestBody UpdatePaymentRequest updatePaymentRequest) {
        PaymentDto paymentDto = paymentService.updatePaymentStatus(updatePaymentRequest);
        if (paymentDto == null) return ResponseEntity.badRequest().body("Payment not updated");
        return new ResponseEntity<>(paymentDto, HttpStatus.OK);
    }
}
