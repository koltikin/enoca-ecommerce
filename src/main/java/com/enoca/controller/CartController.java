package com.enoca.controller;

import com.enoca.dto.CartDto;
import com.enoca.dto.ResponseWrapper;
import com.enoca.service.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cart")
@SecurityRequirement(name="keycloak")
@Tag(name = "Carts", description = "Operations related to carts")
public class CartController {
    private final CartService cartService;

    @Operation(summary = "Get all carts only root user can do this)",
            description = "This endpoint allows root user to get all the carts")
    @ApiResponses(value = {
            @ApiResponse(
                    content = @Content(mediaType = "application/json"))
    })

    @PreAuthorize("hasAnyRole('user','root')")
    @GetMapping(value = "/list", produces = "application/json")
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

    @Operation(summary = "Get cart by customer id)",
            description = "This endpoint allows you to get customer car by customer id, you can pass customer id as pathVariable")

    @PreAuthorize("hasAnyRole('user','root')")
    @GetMapping(value = "/{customerId}", produces = "application/json")
    public ResponseEntity<ResponseWrapper> getCart(@PathVariable(required = false) long customerId){
        CartDto cart = cartService.getCart(customerId);
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseWrapper.builder()
                        .success(true)
                        .code(HttpStatus.OK.value())
                        .message("cart is successfully retrieved")
                        .data(cart)
                        .build()
        );
    }

    @Operation(summary = "add product to customer cart)",
            description = "This endpoint allows you to add product to customer cart, " +
                    "you can pass customer id as pathVariable(if didn't pass it take current login customer) " +
                    "and you must give valid product id as RequestParam")

    @PreAuthorize("hasAnyRole('user','root')")
    @PostMapping(value = "/addproduct/{customerId}", produces = "application/json")
    public ResponseEntity<ResponseWrapper> addProductToCart(@PathVariable(required = false) long customerId,
                                                            @RequestParam long productId){
        CartDto cart = cartService.addProductToCart(customerId, productId);
        return ResponseEntity.ok(ResponseWrapper.builder()
                        .success(true)
                        .code(HttpStatus.OK.value())
                        .message("product successfully added to cart")
                        .data(cart)
                .build());
    }

    @Operation(summary = "update product quantity in the cart)",
            description = "This endpoint allows you to update product quantity in the customer cart, " +
                    "you can pass customer id as pathVariable(if didn't pass it take current login customer) " +
                    "and you must give valid product id and quantity as RequestParam")

    @PreAuthorize("hasAnyRole('user','root')")
    @PutMapping(value = "/update/{customerId}", produces = "application/json")
    public ResponseEntity<ResponseWrapper> updateCart(@PathVariable(required = false)  long customerId,
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

    @Operation(summary = "empty all the products in the cart)",
            description = "This endpoint allows you to empty all the products in the customer cart, " +
                    "you can pass customer id as pathVariable(if didn't pass it take current login customer)")

    @PreAuthorize("hasAnyRole('user','root')")
    @GetMapping(value = "/empty/{customerId}", produces = "application/json")
    public ResponseEntity<ResponseWrapper> emptyCart(@PathVariable(required = false) long customerId){
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

    @Operation(summary = "remove specific product the cart)",
            description = "This endpoint allows you to remove specific product from the customer cart, " +
                    "you can pass customer id as pathVariable(if didn't pass it take current login customer)")

    @PreAuthorize("hasAnyRole('user','root')")
    @PutMapping(value = "/removeProduct/{customerId}", produces = "application/json")
    public ResponseEntity<ResponseWrapper> removeProductFromCart(@PathVariable Long customerId, @RequestParam Long productId) {
        CartDto cartDto = cartService.removeProductFromCart(customerId, productId);
        return ResponseEntity.ok(ResponseWrapper.builder()
                        .success(true)
                        .code(HttpStatus.OK.value())
                        .message("The product successfully removed from cart")
                        .data(cartDto)
                .build());

    }
}
