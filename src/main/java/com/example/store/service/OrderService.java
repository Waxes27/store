package com.example.store.service;

import com.example.store.dto.order.OrderCreateDTO;
import com.example.store.entity.Customer;
import com.example.store.entity.Order;
import com.example.store.mapper.OrderMapper;
import com.example.store.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public List<Order> findAllCustomerOrdersByCustomerId(Long customerId) {
        return orderRepository.findAllByCustomerId(customerId);
    }

    @Cacheable(value = "ordersByCustomerIdPaginated", key = "#customerId + '_' + #pageable.pageNumber + '_' + #pageable.pageSize")
    public Page<Order> findAllCustomerOrdersByCustomerId(Long customerId, Pageable pageable) {
        return orderRepository.findAllByCustomerId(customerId, pageable);
    }

    @Cacheable(value = "ordersByCustomerName", key = "#name")
    public List<Order> findOrdersByCustomerName(String name) {
        return orderRepository.findAllByCustomerName(name);
    }

    @Cacheable(value = "ordersByCustomerNamePaginated", key = "#name + '_' + #pageable.pageNumber + '_' + #pageable.pageSize")
    public Page<Order> findOrdersByCustomerName(String name, Pageable pageable) {
        return orderRepository.findAllByCustomerName(name, pageable);
    }

    @Cacheable(value = "allOrders")
    public List<Order> findAllOrders() {
        return orderRepository.findAll();
    }

    @Cacheable(value = "allOrdersPaginated", key = "#pageable.pageNumber + '_' + #pageable.pageSize")
    public Page<Order> findAllOrders(Pageable pageable) {
        return orderRepository.findAll(pageable);
    }

    @CacheEvict(
            value = {"orders", "ordersByCustomerId", "ordersByCustomerName", "allOrders", 
                    "ordersByCustomerIdPaginated", "ordersByCustomerNamePaginated", "allOrdersPaginated"},
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
