package com.enoca.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
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

    @NotNull
    private CartDto cart;

    private OrderDto order;

    @Min(0)
    private BigDecimal price;
    @Min(1)
    private int quantity;

}
