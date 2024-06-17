package com.enoca.service;

import com.enoca.dto.CartItemDto;

import java.util.Optional;

public interface  CartItemService {
    void save(CartItemDto foundCartItem);
}
