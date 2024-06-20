package com.enoca.controller;

import com.enoca.dto.ProductDto;
import com.enoca.dto.ResponseWrapper;
import com.enoca.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product")
@Tag(name = "Products", description = "Operations related to products")
public class ProductController {
    private final ProductService productService;

    @Operation(summary = "Get all the products)",
            description = "This endpoint allows you to get all the products")
    @GetMapping(value = "/list", produces = "application/json")
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

    @Operation(summary = "Get specific products)",
            description = "This endpoint allows you to get specific product")
    @GetMapping(value = "/{productId}", produces = "application/json")
    public ResponseEntity<ResponseWrapper> getProduct(@PathVariable Long productId){
        return ResponseEntity.ok(
                ResponseWrapper.builder()
                        .success(true)
                        .code(HttpStatus.OK.value())
                        .message("Product is successfully retrieved")
                        .data(productService.getProductById(productId))
                        .build()
        );
    }

    @Operation(summary = "Create new product only root user can do this)",
            description = "This endpoint allows you to create a new product." +
                    "productName, price and inStockQuantity are required fields")

    @PostMapping(value = "/create", consumes = "application/json", produces = "application/json")
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

    @Operation(summary = "update product only root user can do this)",
            description = "This endpoint allows you to update product." +
                    "productName, price and inStockQuantity are required fields " +
                    "and you should give valid product id as pathVariable")

    @PutMapping(value = "/update/{productId}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<ResponseWrapper> updateProduct(@Valid @RequestBody ProductDto productDto, @PathVariable Long productId){
        var updatedProduct = productService.update(productDto,productId);

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

    @Operation(summary = "delete product only root user can do this)",
            description = "This endpoint allows you to delete product." +
                    "you should give valid product id as pathVariable")

    @DeleteMapping(value = "/delete/{productId}", produces = "application/json")
    public ResponseEntity<ResponseWrapper> deleteProduct(@PathVariable Long productId){
        var deletedProduct = productService.delete(productId);

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
