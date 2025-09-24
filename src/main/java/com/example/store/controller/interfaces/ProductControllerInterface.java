package com.example.store.controller.interfaces;

import com.example.store.controller.interfaces.openapi.ProductControllerDefinitions;
import com.example.store.dto.product.ProductCreateDTO;
import com.example.store.dto.product.ProductDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/products")
public interface ProductControllerInterface extends ProductControllerDefinitions {

    @GetMapping
    List<ProductDTO> getAllProducts();

    @GetMapping(params = {"page", "size"})
    Page<ProductDTO> getAllProducts(Pageable pageable);

    @PostMapping
    ProductDTO createProduct(ProductCreateDTO productCreateDTO);

    @GetMapping("/{id}")
    ProductDTO getProductById(@PathVariable Long id);
}
