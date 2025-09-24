package com.example.store.controller.interfaces;

import com.example.store.controller.interfaces.openapi.CustomerControllerDefinitions;
import com.example.store.dto.customer.CustomerCreateDTO;
import com.example.store.dto.customer.CustomerDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/customer")
public interface CustomerControllerInterface extends CustomerControllerDefinitions {

    @GetMapping
    public List<CustomerDTO> getAllCustomers();

    @GetMapping(params = {"page", "size"})
    public Page<CustomerDTO> getAllCustomers(Pageable pageable);

    @PostMapping
    public CustomerDTO createCustomer(CustomerCreateDTO customer);

    @GetMapping("/name")
    public List<CustomerDTO> getCustomerByName(@RequestParam String customerName);

    @GetMapping(path = "/name", params = {"page", "size"})
    public Page<CustomerDTO> getCustomerByName(@RequestParam String customerName, Pageable pageable);
}
