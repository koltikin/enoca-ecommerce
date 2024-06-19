package com.enoca.entity;

import jakarta.persistence.*;
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
    private BigDecimal price;
    private int quantity;
}
