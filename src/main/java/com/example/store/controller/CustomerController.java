package com.example.store.controller;

import com.example.store.controller.interfaces.CustomerControllerInterface;
import com.example.store.dto.customer.CustomerCreateDTO;
import com.example.store.dto.customer.CustomerDTO;
import com.example.store.entity.Customer;
import com.example.store.mapper.CustomerMapper;
import com.example.store.service.CustomerService;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CustomerController implements CustomerControllerInterface {

    private final CustomerService customerService;
    private final CustomerMapper customerMapper;

    // TODO look at getting latency down ( Caching )
    @Override
    public List<CustomerDTO> getAllCustomers() {
        return customerMapper.customersToCustomerDTOs(customerService.findAllCustomers());
    }

    @Override
    public CustomerDTO createCustomer(@RequestBody CustomerCreateDTO customer) {
        Customer savedCustomer = customerService.saveCustomer(customerMapper.customerCreateDTOtoCustomer(customer));
        return customerMapper.customerToCustomerDTO(savedCustomer);
    }

    @Override
    public List<CustomerDTO> getCustomerByName(@RequestParam String customerName) {
        return customerMapper.customersToCustomerDTOs(customerService.findCustomerByName(customerName));
    }

    @Override
    public Page<CustomerDTO> getAllCustomers(Pageable pageable) {
        Page<Customer> customerPage = customerService.findAllCustomers(pageable);
        return customerPage.map(customerMapper::customerToCustomerDTO);
    }

    @Override
    public Page<CustomerDTO> getCustomerByName(String customerName, Pageable pageable) {
        Page<Customer> customerPage = customerService.findCustomerByName(customerName, pageable);
        return customerPage.map(customerMapper::customerToCustomerDTO);
    }
}
