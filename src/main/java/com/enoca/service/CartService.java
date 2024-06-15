package com.enoca.service;

import com.enoca.dto.CartDto;

public interface CartService {
    CartDto getCart(long id);

    CartDto updateCart(CartDto cartDto, long id);

    CartDto emptyCart(long customerId);
}
