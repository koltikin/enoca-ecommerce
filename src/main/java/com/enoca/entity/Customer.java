package com.enoca.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@Entity
@Table(name = "customers")
public class Customer extends BaseEntity {

    private String firstName;
    private String lastName;
    private String email;
    private String passWord;

    @OneToOne
    private Cart cart;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Order> orders;
}
