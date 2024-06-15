package com.enoca.service;

import com.enoca.dto.CartDto;

public interface CartService {
    CartDto getCart(long id);

    CartDto updateCart(CartDto cartDto, long id);

    CartDto emptyCart(long customerId);

    CartDto findByCustomerId(Long customerId);

    void save(CartDto cart);

    CartDto addProductToCart(Long customerId, Long productId, int quantity);

    CartDto removeProductFromCart(Long customerId, Long productId, int quantity);
}