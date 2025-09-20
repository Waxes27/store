package com.example.store.controller.interfaces.openapi;

import com.example.store.dto.customer.CustomerCreateDTO;
import com.example.store.dto.customer.CustomerDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;

public interface CustomerControllerDefinitions {

    @Operation(summary = "Get all customers")
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Returns Customers if successful",
                        content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CustomerDTO.class))
                        }),
            })
    public List<CustomerDTO> getAllCustomers();

    @Operation(summary = "Get customer by name or name portion")
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Get Customer(s) if successful",
                        content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CustomerDTO.class))
                        }),
                @ApiResponse(responseCode = "404", description = "Customer not found"),
            })
    public List<CustomerDTO> getCustomerByName(String customerName);

    @Operation(summary = "Create a new customers")
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "201",
                        description = "Created Customers",
                        content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CustomerDTO.class))
                        }),
                @ApiResponse(responseCode = "404", description = "Invalid id supplied"),
            })
    public CustomerDTO createCustomer(CustomerCreateDTO customer);
}
