package com.enoca.service.impl;

import com.enoca.dto.CustomerDto;
import com.enoca.entity.Customer;
import com.enoca.mapper.MapperUtil;
import com.enoca.repository.CustomerRepository;
import com.enoca.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository repository;
    private final MapperUtil mapper;

    @Override
    public CustomerDto createCustomer(CustomerDto customerDto) {
        Customer customer = mapper.convert(customerDto, new Customer());
        Customer savedCustomer = repository.save(customer);
        return mapper.convert(savedCustomer, new CustomerDto());
    }

    @Override
    public List<CustomerDto> getAllCustomer() {
        List<Customer> customers = repository.getAllByIsDeleted(false);
        return customers.stream()
                .map(customer -> mapper.convert(customer, new CustomerDto()))
                .toList();
    }
}
