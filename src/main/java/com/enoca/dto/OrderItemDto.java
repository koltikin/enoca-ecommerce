package com.enoca.dto;

import com.enoca.entity.Order;
import com.enoca.entity.Product;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.JoinColumn;
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
    private Product product;

    private Order order;

    private BigDecimal price;
    private int quantity;

}
