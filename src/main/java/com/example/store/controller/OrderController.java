package com.example.store.controller;

import com.example.store.controller.interfaces.OrderControllerInterface;
import com.example.store.dto.order.OrderCreateDTO;
import com.example.store.dto.order.OrderDTO;
import com.example.store.mapper.OrderMapper;
import com.example.store.service.OrderService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController implements OrderControllerInterface {

    private final OrderService orderService;
    private final OrderMapper orderMapper;

    @Override
    public List<OrderDTO> getAllOrders() {
        return orderMapper.ordersToOrderDTOs(orderService.findAllOrders());
    }

    @Override
    public OrderDTO createOrder(@RequestBody OrderCreateDTO orderCreateDTO) {
        return orderMapper.orderToOrderDTO(orderService.saveOrder(orderCreateDTO));
    }

    @Override
    public OrderDTO getOrderById(@PathVariable Long id) {
        return orderMapper.orderToOrderDTO(orderService.findOrderById(id));
    }
}
