package com.example.store.mapper;

import com.example.store.dto.order.OrderCreateDTO;
import com.example.store.dto.order.OrderCustomerDTO;
import com.example.store.dto.order.OrderDTO;
import com.example.store.entity.Customer;
import com.example.store.entity.Order;

import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderDTO orderToOrderDTO(Order order);

    List<OrderDTO> ordersToOrderDTOs(List<Order> orders);

    OrderCustomerDTO orderToOrderCustomerDTO(Customer customer);

    Order orderDTOToOrder(OrderDTO orderDTO);

    Order orderDTOToOrder(OrderCreateDTO orderCreateDTO);
}
