package com.example.store.dto.order;

import com.example.store.dto.product.ProductDTO;

import lombok.Data;

import java.util.List;

@Data
public class OrderDTO {
    private Long id;
    private String description;
    private OrderCustomerDTO customer;
    private List<ProductDTO> products;
}
