package com.enoca.service.impl;

import com.enoca.dto.CartItemDto;
import com.enoca.entity.CartItem;
import com.enoca.mapper.MapperUtil;
import com.enoca.repository.CartItemRepository;
import com.enoca.service.CartItemService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService {
    private final CartItemRepository repository;
    private final MapperUtil mapper;
    @Override
    @Transactional
    public void save(CartItemDto cartItemDto) {
        repository.save(mapper.convert(cartItemDto, new CartItem()));
    }

    @Override
    @Transactional
    public CartItemDto saveCartItem(CartItemDto cartItem) {
        CartItem savedCartItem = repository.save(mapper.convert(cartItem, new CartItem()));
        return mapper.convert(savedCartItem, new CartItemDto());
    }
}
