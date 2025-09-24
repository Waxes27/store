package com.example.store.service;

import com.example.store.entity.Customer;
import com.example.store.entity.Order;
import com.example.store.repository.CustomerRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    @Cacheable(value = "customers", key = "#id")
    public Customer findCustomerById(Long id) {
        return customerRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Customer with id: " + id + " not found"));
    }

    @Cacheable(value = "customersByName", key = "#potentialCustomerName")
    public List<Customer> findCustomerByName(String potentialCustomerName) {
        return customerRepository.findByNameContainingIgnoreCase(potentialCustomerName);
    }

    @Cacheable(value = "customersByNamePaginated", key = "#potentialCustomerName + '_' + #pageable.pageNumber + '_' + #pageable.pageSize")
    public Page<Customer> findCustomerByName(String potentialCustomerName, Pageable pageable) {
        return customerRepository.findByNameContainingIgnoreCase(potentialCustomerName, pageable);
    }

    @Cacheable(value = "allCustomers")
    public List<Customer> findAllCustomers() {
        return customerRepository.findAll();
    }

    @Cacheable(value = "allCustomersPaginated", key = "#pageable.pageNumber + '_' + #pageable.pageSize")
    public Page<Customer> findAllCustomers(Pageable pageable) {
        return customerRepository.findAll(pageable);
    }

    @CacheEvict(
            value = {"customers", "allCustomers", "customersByName", "allCustomersPaginated", "customersByNamePaginated"},
            allEntries = true)
    public Customer saveCustomer(Customer customer) {
        Optional<Customer> dbCustomer = customerRepository.findById(customer.getId());

        if (dbCustomer.isPresent()) {
            throw new RuntimeException("Customer with id " + customer.getId() + " already exists");
        }
        return customerRepository.save(customer);
    }

    @CacheEvict(
            value = {"customers", "allCustomers", "customersByName", "allCustomersPaginated", "customersByNamePaginated"},
            allEntries = true)
    public void updateCustomerOrder(Order order, Long customerId) {
        Optional<Customer> dbCustomer = customerRepository.findById(customerId);
        if (dbCustomer.isEmpty()) {
            throw new RuntimeException("Customer with id " + customerId + " not found");
        }

        Customer customer = dbCustomer.get();
        customer.addOrder(order);
        customerRepository.save(customer);
    }
}
