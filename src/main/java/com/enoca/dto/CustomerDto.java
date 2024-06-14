package com.enoca.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerDto {
    private long id;
    private String firstName;
    private String lastName;
    private String email;

    @JsonIgnore
    private String passWord;
    private CartDto cart;
    private List<OrderDto> orders;
}
