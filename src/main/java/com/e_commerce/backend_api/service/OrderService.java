package com.e_commerce.backend_api.service;

import com.e_commerce.backend_api.model.*;
import com.e_commerce.backend_api.repositories.CartRepository;
import com.e_commerce.backend_api.repositories.OrderRepository;
import com.e_commerce.backend_api.repositories.ProductRepository;
import com.e_commerce.backend_api.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final CartService cartService;

    public OrderDto createOrder(String userId) {
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
        List<OrderItemDto> orderItems = new ArrayList<>();
        double totalAmount = 0.0;
        for (CartItem cartItem : cartItems) {
            Product product = productMap.get(cartItem.getProductId());
            if (product == null) throw new RuntimeException("Product not found: " + cartItem.getProductId());
            if (product.getStock() < cartItem.getQuantity())
                throw new RuntimeException("Insufficient stock for product: " + product.getName());
            double amount = product.getPrice() * cartItem.getQuantity();
            totalAmount += amount;
            orderItems.add(new OrderItemDto(
                    cartItem.getProductId(),
                    cartItem.getQuantity(),
                    product.getPrice()
            ));
        }
        Order order = new Order();
        order.setUserId(userId);
        order.setTotalAmount(totalAmount);
        order.setStatus("CREATED");
        order = orderRepository.create(order);
        cartService.deleteCartItems(user.getId());
        return new OrderDto(
                order.getId(),
                order.getUserId(),
                order.getTotalAmount(),
                order.getStatus(),
                orderItems
        );
    }

    public List<OrderDto> getAllOrders(String userId) {
        User user = userRepository.findById(userId);
        if (user == null) return null;
        List<Order> orders = orderRepository.findByUserId(userId);
        return orders.stream().map(order -> new OrderDto(order.getId(), order.getUserId(), order.getTotalAmount(), order.getStatus(), new ArrayList<>())).toList();
    }
}
