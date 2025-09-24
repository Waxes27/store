package com.example.store.repository;

import com.example.store.entity.Order;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByCustomerId(Long customerId);
    Page<Order> findAllByCustomerId(Long customerId, Pageable pageable);

    List<Order> findAllByCustomerName(String customerName);
    Page<Order> findAllByCustomerName(String customerName, Pageable pageable);
}
