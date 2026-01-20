package com.e_commerce.backend_api.controller;

import com.e_commerce.backend_api.dtos.ProductRequest;
import com.e_commerce.backend_api.model.Product;
import com.e_commerce.backend_api.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody ProductRequest productRequest) {
        try {
            Optional<Product> product = productService.createProduct(productRequest);
            if (product.isPresent()) return new ResponseEntity<>(product, HttpStatus.OK);
            return new ResponseEntity<>("Failed to create product!", HttpStatus.CONFLICT);
        } catch (Exception error) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllProducts() {
        return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<?> getProductsByName(@RequestParam String q) {
        return new ResponseEntity<>(productService.getProductsByName(q) , HttpStatus.OK);
    }
}
