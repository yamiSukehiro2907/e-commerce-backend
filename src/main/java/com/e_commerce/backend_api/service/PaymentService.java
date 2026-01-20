package com.e_commerce.backend_api.service;

import com.e_commerce.backend_api.dtos.PaymentDto;
import com.e_commerce.backend_api.dtos.PaymentRequest;
import com.e_commerce.backend_api.dtos.WebHookResponse;
import com.e_commerce.backend_api.model.Order;
import com.e_commerce.backend_api.model.Payment;
import com.e_commerce.backend_api.repositories.OrderRepository;
import com.e_commerce.backend_api.repositories.PaymentRepository;
import com.e_commerce.backend_api.webhook.PaymentMockService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final PaymentMockService mockService;

    public ResponseEntity<?> createPayment(PaymentRequest paymentRequest) {
        Order order = orderRepository.findById(paymentRequest.orderId());
        if (order == null) return new ResponseEntity<>(Map.of("message", "Order not found!"), HttpStatus.NOT_FOUND);
        if (!Objects.equals(order.getStatus(), "CREATED"))
            return new ResponseEntity<>(Map.of("message", "Order not available for payment!"), HttpStatus.NOT_FOUND);
        List<Payment> payments = paymentRepository.findByOrderId(paymentRequest.orderId());
        for (Payment payment : payments) {
            if (payment.getStatus().equals("PAID"))
                return new ResponseEntity<>(Map.of("message", "Payment already done for this Order!!"), HttpStatus.CONFLICT);
        }
        Payment payment = new Payment();
        payment.setOrderId(order.getId());
        payment.setAmount(paymentRequest.amount());
        payment.setStatus("PENDING");
        payment.setCreatedAt(LocalDateTime.now());
        payment = paymentRepository.createPayment(payment);
        PaymentDto dto = new PaymentDto(payment.getId(), payment.getOrderId(), payment.getAmount(), payment.getStatus(), payment.getTransactionId());
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    public ResponseEntity<?> processPayment(String paymentId) {
        Payment payment = paymentRepository.findById(paymentId);
        if (payment == null) return new ResponseEntity<>(Map.of("message", "Payment not found!"), HttpStatus.NOT_FOUND);
        if (!payment.getStatus().equals("PENDING"))
            return new ResponseEntity<>(Map.of("message", "Payment not available for completion!"), HttpStatus.NOT_FOUND);
        WebHookResponse webHookResponse = mockService.updatePaymentStatus();
        payment.setStatus(webHookResponse.status());
        payment.setTransactionId(webHookResponse.transactionId());
        payment = paymentRepository.save(payment);
        Order order = orderRepository.findById(payment.getOrderId());
        if (order == null)
            return new ResponseEntity<>(Map.of("message", "Payment not available for completion!"), HttpStatus.NOT_FOUND);
        order.setStatus("SHIPPED");
        orderRepository.save(order);
        PaymentDto dto = new PaymentDto(payment.getId(), payment.getOrderId(), payment.getAmount(), payment.getStatus(), payment.getTransactionId());
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    public List<PaymentDto> getAllPayments() {
        List<Payment> payments = paymentRepository.findAll();
        return payments.stream().map(payment ->
                new PaymentDto(payment.getId(), payment.getOrderId(), payment.getAmount(), payment.getStatus(), payment.getTransactionId())
        ).toList();
    }
}
/*

[
    {
        "id": "696f5813e2ca9bbb3134652b",
        "userId": "696f5730e2ca9bbb31346524",
        "totalAmount": 310000.0,
        "status": "CANCELLED",
        "items": [
            {
                "productId": "696f574be2ca9bbb31346525",
                "quantity": 3,
                "price": 100000.0
            },
            {
                "productId": "696f5767e2ca9bbb31346526",
                "quantity": 10,
                "price": 1000.0
            }
        ]
    },
    {
        "id": "696f583fe2ca9bbb3134652f",
        "userId": "696f5730e2ca9bbb31346524",
        "totalAmount": 10000.0,
        "status": "CREATED",
        "items": [
            {
                "productId": "696f5767e2ca9bbb31346526",
                "quantity": 10,
                "price": 1000.0
            }
        ]
    }
]
*/