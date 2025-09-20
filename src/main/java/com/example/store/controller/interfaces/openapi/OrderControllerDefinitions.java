package com.example.store.controller.interfaces.openapi;

import com.example.store.dto.order.OrderCreateDTO;
import com.example.store.dto.order.OrderDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;

public interface OrderControllerDefinitions {

    @Operation(summary = "Get all orders")
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Returns Orders if successful",
                        content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = OrderDTO.class))
                        }),
            })
    public List<OrderDTO> getAllOrders();

    @Operation(summary = "Create new order")
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "201",
                        description = "Returns Order if successfully created",
                        content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = OrderDTO.class))
                        }),
            })
    public OrderDTO createOrder(OrderCreateDTO order);

    @Operation(summary = "Get order by Id")
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "201",
                        description = "Returns Order if successfully created",
                        content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = OrderDTO.class))
                        }),
            })
    public OrderDTO getOrderById(Long id);
}
