package com.enoca.dto;

import com.enoca.entity.Cart;
import com.enoca.entity.Product;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CartItemDto {

    private Product product;
    private Cart cart;

    private int quantity;
}
