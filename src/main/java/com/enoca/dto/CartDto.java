package com.enoca.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class CartDto {
    private Long id;
    private CustomerDto customer;
    private List<OrderItemDto> items;
    private BigDecimal totalPrice;
}
