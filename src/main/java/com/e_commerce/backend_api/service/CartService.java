package com.e_commerce.backend_api.service;

import com.e_commerce.backend_api.dtos.CartItemDto;
import com.e_commerce.backend_api.dtos.ItemRequest;
import com.e_commerce.backend_api.dtos.ProductDto;
import com.e_commerce.backend_api.model.CartItem;
import com.e_commerce.backend_api.model.Product;
import com.e_commerce.backend_api.model.User;
import com.e_commerce.backend_api.repositories.CartRepository;
import com.e_commerce.backend_api.repositories.ProductRepository;
import com.e_commerce.backend_api.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public CartItem addToCart(ItemRequest itemRequest) {
        User user = userRepository.findById(itemRequest.userId());
        if (user == null) return null;
        Product product = productRepository.findById(itemRequest.productId());
        if (product == null) return null;
        CartItem cartItem = new CartItem();
        cartItem.setUserId(itemRequest.userId());
        cartItem.setProductId(itemRequest.productId());
        cartItem.setQuantity(itemRequest.quantity());
        return cartRepository.addToCart(cartItem);
    }

    public List<CartItemDto> findItemsInCart(String userId) {
        User user = userRepository.findById(userId);
        if (user == null) return null;
        List<CartItem> cartItems = cartRepository.findByUserId(userId);
        return cartItems.stream().map(cartItem -> {
            Product product = productRepository.findById(cartItem.getProductId());
            if (product == null) return null;
            ProductDto productDto = new ProductDto(product.getId(), product.getName(), product.getPrice());
            return new CartItemDto(cartItem.getId(), cartItem.getProductId(), cartItem.getQuantity(), productDto);
        }).toList().stream().filter(Objects::nonNull).toList();
    }

    public void deleteCartItems(String userId) {
        User user = userRepository.findById(userId);
        if (user == null) return;
        cartRepository.emptyCart(userId);
    }
}
