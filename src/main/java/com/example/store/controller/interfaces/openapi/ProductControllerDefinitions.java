package com.example.store.controller.interfaces.openapi;

import com.example.store.dto.product.ProductCreateDTO;
import com.example.store.dto.product.ProductDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;

public interface ProductControllerDefinitions {

    @Operation(summary = "Get all products")
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Returns products if any",
                        content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ProductDTO.class))
                        }),
            })
    public List<ProductDTO> getAllProducts();

    @Operation(summary = "Create new product")
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "201",
                        description = "Returns Product if successfully created",
                        content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ProductDTO.class))
                        }),
            })
    public ProductDTO createProduct(ProductCreateDTO productCreateDTO);

    @Operation(summary = "Get order by Id")
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "201",
                        description = "Returns Order if successfully created",
                        content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ProductDTO.class))
                        }),
            })
    public ProductDTO getProductById(Long id);
}
