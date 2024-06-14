package com.enoca.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "orderItems")
public class OrderItem extends BaseEntity{

    @ManyToOne
    private Product product;

    @ManyToOne
    @JoinColumn(name = "cart-id")
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "order-id")
    private Order order;

    private BigDecimal price;
    private int quantity;
}
