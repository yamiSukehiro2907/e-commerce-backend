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

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final PaymentMockService mockService;

    public ResponseEntity<?> createPayment(PaymentRequest paymentRequest) {
        Order order = orderRepository.findById(paymentRequest.orderId());
        if (order == null) return new ResponseEntity<>(Map.of("message", "Order not found!"), HttpStatus.NOT_FOUND);
        if (!Objects.equals(order.getStatus(), "CREATED"))
            return new ResponseEntity<>(Map.of("message", "Order not available for payment!"), HttpStatus.NOT_FOUND);
        Payment payment = new Payment();
        payment.setOrderId(order.getId());
        payment.setAmount(paymentRequest.amount());
        payment.setStatus("PENDING");
        payment = paymentRepository.createPayment(payment);
        PaymentDto dto = new PaymentDto(payment.getPaymentId(), payment.getOrderId(), payment.getAmount(), payment.getStatus(), payment.getTransactionId());
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
        Order order = orderRepository.findByPaymentId(payment.getPaymentId());
        order.setStatus("SHIPPED");
        orderRepository.save(order);
        PaymentDto dto = new PaymentDto(payment.getPaymentId(), payment.getOrderId(), payment.getAmount(), payment.getStatus(), payment.getTransactionId());
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    public List<PaymentDto> getAllPayments() {
        List<Payment> payments = paymentRepository.findAll();
        return payments.stream().map(payment ->
                new PaymentDto(payment.getPaymentId(), payment.getOrderId(), payment.getAmount(), payment.getStatus(), payment.getTransactionId())
        ).toList();
    }
}
