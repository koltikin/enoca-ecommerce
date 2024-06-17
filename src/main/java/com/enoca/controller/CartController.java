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

    @GetMapping("/list")
    public ResponseEntity<ResponseWrapper> getAllCarts(){
        return ResponseEntity.ok().body(
                ResponseWrapper.builder()
                        .success(true)
                        .message("Carts are successfully retrieved")
                        .code(HttpStatus.OK.value())
                        .data(cartService.findAllCarts())
                        .build()
        );
    }

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

    @PostMapping("/addproduct")
    public ResponseEntity<ResponseWrapper> addProductToCart(@RequestParam(required = false) long customerId,
                                                            @RequestParam long productId){
        CartDto cart = cartService.addProductToCart(customerId, productId);
        return ResponseEntity.ok(ResponseWrapper.builder()
                        .success(true)
                        .code(HttpStatus.OK.value())
                        .message("product successfully added to cart")
                        .data(cart)
                .build());
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseWrapper> updateCart(@RequestParam(required = false) long customerId,
                                                      @RequestParam long productId, @RequestParam int quantity){
        CartDto updatedCart = cartService.updateCart(customerId, productId,quantity);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(
                ResponseWrapper.builder()
                        .success(true)
                        .code(HttpStatus.ACCEPTED.value())
                        .message("cart is successfully updated")
                        .data(updatedCart)
                        .build()
        );
    }

    @GetMapping("/empty")
    public ResponseEntity<ResponseWrapper> emptyCart(@RequestParam(required = false) long customerId){
        CartDto updatedCart = cartService.emptyCart(customerId);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(
                ResponseWrapper.builder()
                        .success(true)
                        .code(HttpStatus.ACCEPTED.value())
                        .message("cart is successfully Emptied")
                        .data(updatedCart)
                        .build()
        );
    }

    @PutMapping("/removeProduct")
    public ResponseEntity<ResponseWrapper> removeProductFromCart(@RequestParam Long customerId, @RequestParam Long productId) {
        CartDto cartDto = cartService.removeProductFromCart(customerId, productId);
        return ResponseEntity.ok(ResponseWrapper.builder()
                        .success(true)
                        .code(HttpStatus.OK.value())
                        .message("The product successfully removed from cart")
                        .data(cartDto)
                .build());

    }
}
