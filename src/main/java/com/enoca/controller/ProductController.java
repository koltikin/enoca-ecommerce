package com.enoca.controller;

import com.enoca.dto.ProductDto;
import com.enoca.dto.ResponseWrapper;
import com.enoca.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product")
public class ProductController {
    private final ProductService productService;

    @GetMapping("/list")
    public ResponseEntity<ResponseWrapper> getProductList(){
        return ResponseEntity.ok(
                ResponseWrapper.builder()
                        .success(true)
                        .code(HttpStatus.OK.value())
                        .message("Products are successfully retried")
                        .data(productService.getAllCustomer())
                        .build()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseWrapper> getProduct(@PathVariable Long id){
        return ResponseEntity.ok(
                ResponseWrapper.builder()
                        .success(true)
                        .code(HttpStatus.OK.value())
                        .message("Product is successfully retried")
                        .data(productService.getProductById(id))
                        .build()
        );
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseWrapper> createProduct(@RequestBody ProductDto productDto){
        var createdProduct = productService.create(productDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        ResponseWrapper.builder()
                                .success(true)
                                .code(HttpStatus.CREATED.value())
                                .message("Product is successfully created")
                                .data(createdProduct)
                                .build()
                );
    }

}