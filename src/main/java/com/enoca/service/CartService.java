package com.enoca.service;

import com.enoca.dto.CartDto;
import com.enoca.entity.Cart;

import java.util.List;

public interface CartService {
    CartDto getCart(long id);

    CartDto updateCart(long customerId, long productId, int quantity);

    CartDto emptyCart(long customerId);

    CartDto findByCustomerId(Long customerId);

    CartDto saveCart(CartDto cart);

    CartDto addProductToCart(long customerId, long productId);

    CartDto removeProductFromCart(Long customerId, Long productId, int quantity);

    List<CartDto> findAllCarts();

    CartDto createCart(CartDto cartDto);
}
