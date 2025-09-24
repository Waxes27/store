package com.example.store.controller;

import com.example.store.controller.interfaces.ProductControllerInterface;
import com.example.store.dto.product.ProductCreateDTO;
import com.example.store.dto.product.ProductDTO;
import com.example.store.entity.Product;
import com.example.store.mapper.ProductMapper;
import com.example.store.service.ProductService;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductController implements ProductControllerInterface {

    private final ProductMapper productMapper;
    private final ProductService productService;

    @Override
    public List<ProductDTO> getAllProducts() {
        return productMapper.productsToProductDTOs(productService.getAllProducts());
    }

    @Override
    public ProductDTO createProduct(ProductCreateDTO productCreateDTO) {
        Product product = productMapper.productCreateDTOToProduct(productCreateDTO);
        Product savedProduct = productService.save(product);
        return productMapper.productToProductDTO(savedProduct);
    }

    @Override
    public ProductDTO getProductById(Long id) {
        return productMapper.productToProductDTO(productService.getProductById(id));
    }

    @Override
    public Page<ProductDTO> getAllProducts(Pageable pageable) {
        Page<Product> productPage = productService.getAllProducts(pageable);
        return productPage.map(productMapper::productToProductDTO);
    }
}
