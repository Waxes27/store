package com.example.store.controller.interfaces;

import com.example.store.controller.interfaces.openapi.OrderControllerDefinitions;
import com.example.store.dto.order.OrderCreateDTO;
import com.example.store.dto.order.OrderDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/order")
public interface OrderControllerInterface extends OrderControllerDefinitions {

    @GetMapping
    public List<OrderDTO> getAllOrders();

    @GetMapping(params = {"page", "size"})
    public Page<OrderDTO> getAllOrders(Pageable pageable);

    @PostMapping
    public OrderDTO createOrder(OrderCreateDTO orderCreateDTO);

    @GetMapping("/{id}")
    public OrderDTO getOrderById(Long id);
}
