package com.enoca.controller;

import com.enoca.dto.OrderDto;
import com.enoca.dto.ResponseWrapper;
import com.enoca.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/order")
@SecurityRequirement(name="keycloak")
@Tag(name = "Orders", description = "Operations related to orders")
public class OrderController {
    private final OrderService oderService;

    @Operation(summary = "make order)",
            description = "This endpoint allows you to make order" +
                    "you can pass customer id as pathVariable(if didn't pass the current login customer will be selected)")

    @PostMapping(value = "/{customerId}", produces = "application/json")
    public ResponseEntity<ResponseWrapper> placeOrder(@PathVariable(required = false) Long customerId) {
        OrderDto order =  oderService.placeOrder(customerId);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ResponseWrapper.builder()
                        .success(true)
                        .code(HttpStatus.CREATED.value())
                        .message("Order is successfully created")
                        .data(order)
                        .build()
        );
    }

    @Operation(summary = "get order by orderCode)",
            description = "This endpoint allows you to get order info by orderCode" +
                    "you should pass valid order orderCode as RequestParam")

    @GetMapping(produces = "application/json")
    public ResponseEntity<ResponseWrapper> getOrderForCode(@RequestParam String orderCode) {
        OrderDto order =  oderService.getOrderForCode(orderCode);
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseWrapper.builder()
                        .success(true)
                        .code(HttpStatus.OK.value())
                        .message("Order is successfully retrieved")
                        .data(order)
                        .build()
        );
    }

    @Operation(summary = "get all orders of the customer)",
            description = "This endpoint allows you to get all the orders of the specific customer" +
                    "you can pass customer id as pathVariable(if didn't pass the current login customer will be selected)")

    @GetMapping(value = "/{customerId}", produces = "application/json")
    public ResponseEntity<ResponseWrapper> getAllOrdersForCustomer(@PathVariable(required = false) Long customerId) {
        List<OrderDto> orders =  oderService.getAllOrdersForCustomer(customerId);
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseWrapper.builder()
                        .success(true)
                        .code(HttpStatus.OK.value())
                        .message("Orders are successfully retrieved")
                        .data(orders)
                        .build()
        );
    }

    @Operation(summary = "get all orders only root user can do this)",
            description = "This endpoint allows root user get all the orders")

    @GetMapping(value = "/getall", produces = "application/json")
    public ResponseEntity<ResponseWrapper> getAllOrders() {
        List<OrderDto> orders =  oderService.getAllOrders();
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseWrapper.builder()
                        .success(true)
                        .code(HttpStatus.OK.value())
                        .message("Orders are successfully retrieved")
                        .data(orders)
                        .build()
        );
    }
}
