package com.example.store.dto.order;

import lombok.Data;

import java.util.List;

@Data
public class OrderCreateDTO {
    private Long id;
    private String description;
    private Long customerId;
    private List<Long> productIds;
}
