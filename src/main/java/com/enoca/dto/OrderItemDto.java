package com.enoca.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderItemDto {
    private Long id;
    private ProductDto product;
    private CartDto cart;
    private OrderDto order;
    private BigDecimal price;
    private int quantity;

}
