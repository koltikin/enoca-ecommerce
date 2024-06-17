package com.enoca.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
public class CartItem extends BaseEntity{

    @ManyToOne
    private Product product;
    private int quantity;
}
