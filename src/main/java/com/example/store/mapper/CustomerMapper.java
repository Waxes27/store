package com.example.store.mapper;

import com.example.store.dto.customer.CustomerCreateDTO;
import com.example.store.dto.customer.CustomerDTO;
import com.example.store.entity.Customer;

import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    CustomerDTO customerToCustomerDTO(Customer customer);

    List<CustomerDTO> customersToCustomerDTOs(List<Customer> customer);

    Customer customerCreateDTOtoCustomer(CustomerCreateDTO customerCreateDTO);
}
