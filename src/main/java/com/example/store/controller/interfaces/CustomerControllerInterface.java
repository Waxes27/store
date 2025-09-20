package com.example.store.controller.interfaces;

import com.example.store.controller.interfaces.openapi.CustomerControllerDefinitions;
import com.example.store.dto.customer.CustomerCreateDTO;
import com.example.store.dto.customer.CustomerDTO;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/customer")
public interface CustomerControllerInterface extends CustomerControllerDefinitions {

    @GetMapping
    public List<CustomerDTO> getAllCustomers();

    @PostMapping
    public CustomerDTO createCustomer(CustomerCreateDTO customer);

    @GetMapping("/name")
    public List<CustomerDTO> getCustomerByName(@RequestParam String customerName);
}
