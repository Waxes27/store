package com.example.store.service;

import com.example.store.dto.order.OrderCreateDTO;
import com.example.store.entity.Customer;
import com.example.store.entity.Order;
import com.example.store.mapper.OrderMapper;
import com.example.store.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final CustomerService customerService;
    private final ProductService productService;
    private final OrderMapper orderMapper;

    @Cacheable(value = "orders", key = "#id")
    public Order findOrderById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    @Cacheable(value = "ordersByCustomerId", key = "#customerId")
    public void findAllCustomerOrdersByCustomerId(Long customerId) {
        orderRepository.findAllByCustomerId(customerId);
    }

    @Cacheable(value = "ordersByCustomerName", key = "#name")
    public void findOrdersByCustomerName(String name) {
        orderRepository.findAllByCustomerName(name);
    }

    @Cacheable(value = "allOrders")
    public List<Order> findAllOrders() {
        return orderRepository.findAll();
    }

    @CacheEvict(
            value = {"orders", "ordersByCustomerId", "ordersByCustomerName", "allOrders"},
            allEntries = true)
    public Order saveOrder(OrderCreateDTO orderDTO) {
        Customer customer = customerService.findCustomerById(orderDTO.getCustomerId());
        isProductsVerified(orderDTO.getProductIds());
        Order order = orderMapper.orderDTOToOrder(orderDTO);
        order.setCustomer(customer);
        Order newOrder = orderRepository.save(order);
        customerService.updateCustomerOrder(newOrder, customer.getId());
        return newOrder;
    }

    private void isProductsVerified(List<Long> productIds) {
        for (Long productId : productIds) {
            if (!productService.productExistsById(productId)) {
                throw new IllegalArgumentException("Product with id " + productId + " does not exist");
            }
        }
    }
}
