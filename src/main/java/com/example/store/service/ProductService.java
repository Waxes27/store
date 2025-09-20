package com.example.store.service;

import com.example.store.dto.product.ProductDTO;
import com.example.store.entity.Product;
import com.example.store.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    @CacheEvict(value = {"products", "allProducts", "productExists"}, allEntries = true)
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Cacheable(value = "allProducts")
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Cacheable(value = "products", key = "#id")
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Product with id: " + id + " not found"));
    }

    @Cacheable(value = "productExists", key = "#id")
    public boolean productExistsById(Long id) {
        return productRepository.existsById(id);
    }

}
