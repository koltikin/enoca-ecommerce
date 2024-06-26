package com.enoca.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "carts")
public class Cart extends BaseEntity{

    @OneToOne(fetch = FetchType.LAZY)
    private Customer customer;

    @OneToMany(fetch = FetchType.LAZY)
    private List<CartItem> cartItems;

    private BigDecimal totalPrice;
}
