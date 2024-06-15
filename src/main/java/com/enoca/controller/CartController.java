package com.enoca.controller;

import com.enoca.dto.CartDto;
import com.enoca.dto.ResponseWrapper;
import com.enoca.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cart")
public class CartController {
    private final CartService cartService;

    @GetMapping("/{id}")
    public ResponseEntity<ResponseWrapper> getCart(@PathVariable long id){
        CartDto cart = cartService.getCart(id);
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseWrapper.builder()
                        .success(true)
                        .code(HttpStatus.OK.value())
                        .message("cart is successfully retrieved")
                        .data(cart)
                        .build()
        );
    }

    @GetMapping("/update/{id}")
    public ResponseEntity<ResponseWrapper> updateCart(@RequestBody CartDto cartDto, @PathVariable long id){
        CartDto updatedCart = cartService.updateCart(cartDto, id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(
                ResponseWrapper.builder()
                        .success(true)
                        .code(HttpStatus.ACCEPTED.value())
                        .message("cart is successfully updated")
                        .data(updatedCart)
                        .build()
        );
    }

    @GetMapping("/empty/{id}")
    public ResponseEntity<ResponseWrapper> emptyCart(@PathVariable long id){
        CartDto updatedCart = cartService.emptyCart(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(
                ResponseWrapper.builder()
                        .success(true)
                        .code(HttpStatus.ACCEPTED.value())
                        .message("cart is successfully updated")
                        .data(updatedCart)
                        .build()
        );
    }
}
