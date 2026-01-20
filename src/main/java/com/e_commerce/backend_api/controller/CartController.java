package com.e_commerce.backend_api.controller;

import com.e_commerce.backend_api.dtos.ItemRequest;
import com.e_commerce.backend_api.model.CartItem;
import com.e_commerce.backend_api.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {


    private final CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<?> addToCart(@RequestBody ItemRequest itemRequest) {
        try {
            CartItem cartItem = cartService.addToCart(itemRequest);
            if (cartItem != null) return new ResponseEntity<>(cartItem, HttpStatus.OK);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception error) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getCartItems(@PathVariable String userId) {
        return new ResponseEntity<>(cartService.findItemsInCart(userId), HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteCartItems(@PathVariable String userId) {
        cartService.deleteCartItems(userId);
        return new ResponseEntity<>(Map.of("message", "Cart cleared successfully"), HttpStatus.OK);
    }
}
