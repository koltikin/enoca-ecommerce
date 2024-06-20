package com.enoca.service;

import com.enoca.dto.CartDto;

import java.util.List;

public interface CartService {
    CartDto getCart(long id);

    CartDto updateCart(long customerId, long productId, int quantity);

    CartDto emptyCart(long customerId);

    CartDto findByCustomerId(Long customerId);

    CartDto saveCart(CartDto cart);

    CartDto addProductToCart(long customerId, long productId);

    CartDto removeProductFromCart(Long customerId, Long productId);

    List<CartDto> findAllCarts();

    CartDto createCart(CartDto cartDto);
}
