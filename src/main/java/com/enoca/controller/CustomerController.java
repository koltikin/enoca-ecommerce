package com.enoca.controller;

import com.enoca.dto.CustomerDto;
import com.enoca.dto.ResponseWrapper;
import com.enoca.entity.Customer;
import com.enoca.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/customer")
@Tag(name = "Customers", description = "Operations related to customers")
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping(value = "/list", produces = "application/json" )
    @Operation(summary = "Get all customers, only for root user can do this",
            description = "This endpoint allows root user to get all customers list")
    @ApiResponses(value = {
            @ApiResponse(content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<ResponseWrapper> getAllCustomer(){
        return ResponseEntity.ok(
                ResponseWrapper.builder()
                        .success(true)
                        .code(HttpStatus.OK.value())
                        .message("Customers are successfully retrieved")
                        .data(customerService.getAllCustomer())
                        .build()
        );
    }

    @Operation(summary = "Create customer account)",
            description = "This endpoint allows you to create a new customer account. email used for user name" +
                    "firstName, email and passWord are required fields")
    @ApiResponses(value = {
            @ApiResponse(
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = CustomerDto.class)))
    })

    @PostMapping(value = "/create",consumes = "application/json", produces = "application/json")
    public ResponseEntity<ResponseWrapper> createCustomer(@Valid @RequestBody CustomerDto customer){
        CustomerDto savedCustomer = customerService.createCustomer(customer);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        ResponseWrapper.builder()
                                .success(true)
                                .code(HttpStatus.CREATED.value())
                                .message("customer is successfully created")
                                .data(savedCustomer)
                                .build()
                );
    }
}
