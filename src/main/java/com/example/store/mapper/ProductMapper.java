package com.example.store.mapper;

import com.example.store.dto.product.ProductCreateDTO;
import com.example.store.dto.product.ProductDTO;
import com.example.store.entity.Product;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(target = "orderIds", expression = "java(product.getOrders().stream().map(order -> order.getId()).toList())")
    ProductDTO productToProductDTO(Product product);

    List<ProductDTO> productsToProductDTOs(List<Product> products);

    Product productCreateDTOToProduct(ProductCreateDTO productCreateDTO);
}
