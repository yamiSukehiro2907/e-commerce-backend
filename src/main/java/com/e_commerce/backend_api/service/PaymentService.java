package com.e_commerce.backend_api.service;

import com.e_commerce.backend_api.dtos.PaymentRequest;
import com.e_commerce.backend_api.dtos.UpdatePaymentRequest;
import com.e_commerce.backend_api.model.Order;
import com.e_commerce.backend_api.model.Payment;
import com.e_commerce.backend_api.model.PaymentDto;
import com.e_commerce.backend_api.repositories.OrderRepository;
import com.e_commerce.backend_api.repositories.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    public PaymentDto createPayment(PaymentRequest paymentRequest) {
        Order order = orderRepository.findById(paymentRequest.orderId());
        if (order == null) return null;
        Payment payment = new Payment();
        payment.setOrderId(order.getId());
        payment.setAmount(paymentRequest.amount());
        payment.setStatus("PENDING");
        payment = paymentRepository.createPayment(payment);
        return new PaymentDto(payment.getPaymentId(), payment.getOrderId(), payment.getAmount(), payment.getStatus(), payment.getTransactionId());
    }

    public PaymentDto updatePaymentStatus(UpdatePaymentRequest updatePaymentRequest) {
        Payment payment = paymentRepository.findById(updatePaymentRequest.paymentId());
        if (payment == null) return null;
        payment.setTransactionId(updatePaymentRequest.transactionId());
        payment.setStatus(updatePaymentRequest.status());
        payment = paymentRepository.save(payment);
        return new PaymentDto(payment.getPaymentId(), payment.getOrderId(), payment.getAmount(), payment.getStatus(), payment.getTransactionId());
    }

    public List<PaymentDto> getAllPayments() {
        List<Payment> payments = paymentRepository.findAll();
        return payments.stream().map(payment ->
                new PaymentDto(payment.getPaymentId(), payment.getOrderId(), payment.getAmount(), payment.getStatus(), payment.getTransactionId())
        ).toList();
    }
}
