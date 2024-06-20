package com.enoca.service;

import com.enoca.dto.CartItemDto;


public interface  CartItemService {
    void save(CartItemDto cartItemDto);

    CartItemDto saveCartItem(CartItemDto cartItem);
}
