package com.e_commerce.backend_api.controller;

import com.e_commerce.backend_api.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getOrders(@PathVariable String userId) {
        return new ResponseEntity<>(orderService.getAllOrders(userId), HttpStatus.OK);
    }

    @PostMapping("/user/{userId}")
    public ResponseEntity<?> createOrder(@PathVariable String userId) {
        try {
            return orderService.createOrder(userId);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/{orderId}/cancel")
    public ResponseEntity<?> cancelOrder(@PathVariable String orderId) {
        return orderService.cancelOrder(orderId);
    }
}
