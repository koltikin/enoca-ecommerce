package com.enoca.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CartItemDto {
    @JsonIgnore
    private Long id;

    private ProductDto product;

    private int quantity;
}
