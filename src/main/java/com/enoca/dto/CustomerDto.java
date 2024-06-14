package com.enoca.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto {
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private CartDto cart;
    private List<OrderDto> orders;
}
