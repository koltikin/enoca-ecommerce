package com.enoca.service;

import com.enoca.dto.CustomerDto;

import java.util.List;

public interface CustomerService {
    CustomerDto createCustomer(CustomerDto newCustomer);

    List<CustomerDto> getAllCustomer();
}
