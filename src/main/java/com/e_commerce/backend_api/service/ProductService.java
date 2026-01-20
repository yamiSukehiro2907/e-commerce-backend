package com.e_commerce.backend_api.service;

import com.e_commerce.backend_api.dtos.ProductRequest;
import com.e_commerce.backend_api.model.Product;
import com.e_commerce.backend_api.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Optional<Product> createProduct(ProductRequest productRequest) {
        Product product = new Product();
        product.setDescription(productRequest.description());
        product.setName(productRequest.name());
        product.setPrice(productRequest.price());
        product.setStock(productRequest.stock());
        return productRepository.create(product);
    }

    public List<Product> getAllProducts() {
        return productRepository.getAllProducts();
    }
}
