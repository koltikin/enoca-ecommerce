package com.enoca.dto;

import com.enoca.entity.OrderItem;
import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CartDto {
    private Long id;
    private CustomerDto customer;
    private List<ProductDto> products;
    private BigDecimal totalPrice;
}
