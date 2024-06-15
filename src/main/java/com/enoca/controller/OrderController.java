package com.enoca.controller;

import com.enoca.dto.OrderDto;
import com.enoca.dto.ResponseWrapper;
import com.enoca.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/order")
public class OrderController {
    private final OrderService oderService;

    @PostMapping("/order/{customerId}")
    public ResponseEntity<ResponseWrapper> placeOrder(@PathVariable Long customerId) {
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

    @GetMapping("/order/{orderCode}")
    public ResponseEntity<ResponseWrapper> getOrderForCode(@PathVariable String orderCode) {
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

    @GetMapping("/order/{customerId}")
    public ResponseEntity<ResponseWrapper> getAllOrdersForCustomer(@PathVariable Long customerId) {
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
}
