package com.enoca.service.impl;

import com.enoca.dto.CartItemDto;
import com.enoca.entity.CartItem;
import com.enoca.mapper.MapperUtil;
import com.enoca.repository.CartItemRepository;
import com.enoca.service.CartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService {
    private final CartItemRepository repository;
    private final MapperUtil mapper;
    @Override
    public void save(CartItemDto cartItemDto) {
        repository.save(mapper.convert(cartItemDto, new CartItem()));
    }
}
