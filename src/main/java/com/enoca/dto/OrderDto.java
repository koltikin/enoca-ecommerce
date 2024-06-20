package com.enoca.dto;


import com.enoca.entity.OrderItem;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDto {
    private Long id;

    private CustomerDto customer;
    private List<OrderItemDto> orderItems;
    private BigDecimal totalPrice;
    private String orderCode;
    private LocalDateTime orderDateTime;

}
