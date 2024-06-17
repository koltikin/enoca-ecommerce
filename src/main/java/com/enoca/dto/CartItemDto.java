package com.enoca.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CartItemDto {
    private Long id;

    private ProductDto product;
//    private CartDto cart;

    private int quantity;
}
