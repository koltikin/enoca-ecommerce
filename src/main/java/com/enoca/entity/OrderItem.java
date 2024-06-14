package com.enoca.entity;

import jakarta.persistence.Entity;
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
    private Cart cart;

    @ManyToOne
    private Order order;

    private BigDecimal price;
    private int quantity;
}
