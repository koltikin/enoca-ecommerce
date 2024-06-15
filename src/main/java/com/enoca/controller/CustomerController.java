package com.enoca.controller;

import com.enoca.dto.CustomerDto;
import com.enoca.dto.ResponseWrapper;
import com.enoca.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/customer")
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/list")
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

    @PostMapping("/create")
    public ResponseEntity<ResponseWrapper> createCustomer(@RequestBody CustomerDto customer){
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
