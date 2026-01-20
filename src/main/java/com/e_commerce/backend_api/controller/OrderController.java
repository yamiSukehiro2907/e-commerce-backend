package com.e_commerce.backend_api.controller;

import com.e_commerce.backend_api.dtos.OrderRequest;
import com.e_commerce.backend_api.model.OrderDto;
import com.e_commerce.backend_api.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<?> getOrders(@RequestBody OrderRequest orderRequest) {
        return new ResponseEntity<>(orderService.getAllOrders(orderRequest.userId()), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody OrderRequest orderRequest) {
        try {
            OrderDto orderDto = orderService.createOrder(orderRequest.userId());
            if (orderDto != null) return new ResponseEntity<>(orderDto, HttpStatus.OK);
            return new ResponseEntity<>(Map.of("message", "Cart is Empty!"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
