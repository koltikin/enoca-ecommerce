package com.enoca.service;

import com.enoca.dto.CustomerDto;

import java.util.List;

public interface KeycloakService {
    List<CustomerDto> getUserFromKeycloak();

    CustomerDto createCustomer(CustomerDto customer);
}
