package com.enoca.controller;

import com.enoca.dto.ProductDto;
import com.enoca.dto.ResponseWrapper;
import com.enoca.service.ProductService;
import jakarta.validation.Valid;
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
                        .message("Products are successfully retrieved")
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
                        .message("Product is successfully retrieved")
                        .data(productService.getProductById(id))
                        .build()
        );
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseWrapper> createProduct(@Valid @RequestBody ProductDto productDto){
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

    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseWrapper> updateProduct(@Valid @RequestBody ProductDto productDto, @PathVariable Long id){
        var updatedProduct = productService.update(productDto,id);

        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(
                        ResponseWrapper.builder()
                                .success(true)
                                .code(HttpStatus.ACCEPTED.value())
                                .message("Product is successfully updated")
                                .data(updatedProduct)
                                .build()
                );
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseWrapper> deleteProduct(@PathVariable Long id){
        var deletedProduct = productService.delete(id);

        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(
                        ResponseWrapper.builder()
                                .success(true)
                                .code(HttpStatus.ACCEPTED.value())
                                .message("Product is successfully deleted")
                                .data(deletedProduct)
                                .build()
                );
    }

}
