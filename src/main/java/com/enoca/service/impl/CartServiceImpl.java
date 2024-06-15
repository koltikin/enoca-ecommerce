package com.enoca.service.impl;

import com.enoca.dto.CartDto;
import com.enoca.entity.Cart;
import com.enoca.mapper.MapperUtil;
import com.enoca.repository.CartRepository;
import com.enoca.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepository repository;
    private final MapperUtil mapper;

    @Override
    public CartDto getCart(long id) {
        Cart cart = repository.findByIdAndIsDeleted(id, false)
                .orElseThrow(() -> new NoSuchElementException("No cart found with id: " + id));
        return mapper.convert(cart, new CartDto());
    }

    @Override
    public CartDto updateCart(CartDto cartDto, long id) {
        var cart = repository.findByIdAndIsDeleted(id, false);
        if (cart.isPresent()) {
            Cart cartToBeUpdate = mapper.convert(cartDto, new Cart());
            cartToBeUpdate.setId(id);
            return mapper.convert(repository.save(cartToBeUpdate), new CartDto());
        } else throw new NoSuchElementException("there is No cart with id: " + id);
    }

    @Override
    public CartDto emptyCart(long id) {
        var cart = repository.findByIdAndIsDeleted(id, false);
        if (cart.isPresent()) {
            Cart cartToBeEmpty = cart.get();
            cartToBeEmpty.setOrderItems(List.of());
            cartToBeEmpty.setTotalPrice(BigDecimal.ZERO);
            return mapper.convert(repository.save(cartToBeEmpty), new CartDto());
        } else throw new NoSuchElementException("there is No cart with id: " + id);
    }

    @Override
    public CartDto findByCustomerId(Long customerId) {
        Cart cart = repository.findByCustomerIdAndIsDeleted(customerId,false);
        return mapper.convert(cart, new CartDto());
    }

    @Override
    public void save(CartDto cartDto) {
        repository.save(mapper.convert(cartDto, new Cart()));
    }
}
