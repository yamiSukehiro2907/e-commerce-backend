package com.e_commerce.backend_api.service;

import com.e_commerce.backend_api.dtos.OrderDto;
import com.e_commerce.backend_api.dtos.OrderItemDto;
import com.e_commerce.backend_api.model.*;
import com.e_commerce.backend_api.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final CartService cartService;
    private final OrderItemRepository orderItemRepository;

    public ResponseEntity<?> createOrder(String userId) {
        User user = userRepository.findById(userId);
        if (user == null) return null;
        List<CartItem> cartItems = cartRepository.findByUserId(userId);
        if (cartItems.isEmpty()) throw new RuntimeException("Cart is empty");
        Set<String> productIds = cartItems.stream().map(CartItem::getProductId).collect(Collectors.toSet());
        Map<String, Product> productMap = new HashMap<>();
        for (String productId : productIds) {
            Product product = productRepository.findById(productId);
            productMap.put(productId, product);
        }
        List<OrderItem> orderItems = new ArrayList<>();
        double totalAmount = 0.0;
        for (CartItem cartItem : cartItems) {
            Product product = productMap.get(cartItem.getProductId());
            if (product == null)
                return new ResponseEntity<>(Map.of("message", "Product not found!"), HttpStatus.NOT_FOUND);
            if (product.getStock() < cartItem.getQuantity()) {
                return new ResponseEntity<>(Map.of("message", "Product " + product.getName() + " not available!"), HttpStatus.BAD_REQUEST);
            }
            product.setStock(product.getStock() - cartItem.getQuantity());
            productRepository.save(product);
            double amount = product.getPrice() * cartItem.getQuantity();
            totalAmount += amount;
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(product.getId());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(product.getPrice());
            orderItems.add(orderItem);
        }
        Order order = new Order();
        order.setUserId(userId);
        order.setTotalAmount(totalAmount);
        order.setStatus("CREATED");
        Order finalOrder = orderRepository.create(order);
        cartService.deleteCartItems(user.getId());
        orderItems.forEach(orderItem -> orderItem.setOrderId(finalOrder.getId()));
        List<OrderItemDto> orderItemDto = new ArrayList<>();
        orderItems.forEach(orderItem -> {
            orderItemDto.add(new OrderItemDto(orderItem.getProductId(), orderItem.getQuantity(), orderItem.getPrice()));
            orderItemRepository.save(orderItem);
        });
        OrderDto dto = new OrderDto(
                order.getId(),
                order.getUserId(),
                order.getTotalAmount(),
                order.getStatus(),
                orderItemDto
        );
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    public List<OrderDto> getAllOrders(String userId) {
        User user = userRepository.findById(userId);
        if (user == null) return new ArrayList<>();
        List<Order> orders = orderRepository.findByUserId(userId);
        return orders.stream().map(order -> new OrderDto(order.getId(), order.getUserId(), order.getTotalAmount(), order.getStatus(),
                getOrderItems(order)
        )).toList();
    }

    private List<OrderItemDto> getOrderItems(Order order) {
        List<OrderItem> orderItems = orderItemRepository.findByOrderId(order.getId());
        return orderItems.stream().map(orderItem ->
                        new OrderItemDto(orderItem.getProductId(), orderItem.getQuantity(), orderItem.getPrice()))
                .toList();
    }
}
