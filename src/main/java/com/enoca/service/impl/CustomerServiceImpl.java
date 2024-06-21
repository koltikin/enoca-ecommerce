package com.enoca.service.impl;

import com.enoca.dto.CartDto;
import com.enoca.dto.CustomerDto;
import com.enoca.entity.Customer;
import com.enoca.exception.EnocaEcommerceProjectException;
import com.enoca.mapper.MapperUtil;
import com.enoca.repository.CustomerRepository;
import com.enoca.service.CartService;
import com.enoca.service.CustomerService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository repository;
    private final CartService cartService;
    private final MapperUtil mapper;

    @Override
    @Transactional
    public CustomerDto createCustomer(CustomerDto customerDto) {
        //check customer exist or not
        boolean isCustomerExist = repository.existsByEmailAndIsDeleted(customerDto.getEmail(),false);
        if(isCustomerExist) throw  new EnocaEcommerceProjectException("Customer already exist with Email: " + customerDto.getEmail());

        // save new customer
        Customer customer = mapper.convert(customerDto, new Customer());
        Customer savedCustomer = repository.save(customer);

        // create new cart for new customer
        CartDto cartDto = new CartDto();

        // initialize cart with total price and new customer than save it
        cartDto.setTotalPrice(BigDecimal.ZERO);
        cartDto.setCustomer(mapper.convert(savedCustomer, new CustomerDto()));
        cartService.createCart(cartDto);

        return mapper.convert(savedCustomer, new CustomerDto());
    }

    @Override
    public List<CustomerDto> getAllCustomer() {
        List<Customer> customers = repository.getAllByIsDeletedOrderByFirstNameAsc(false);
        return customers.stream()
                .map(customer -> mapper.convert(customer, new CustomerDto()))
                .toList();
    }

    @Override
    public CustomerDto findById(Long customerId) {
        Customer customer =  repository.findByIdAndIsDeleted(customerId,false)
                .orElseThrow(()-> new EnocaEcommerceProjectException("No customer found with id: " + customerId));

        return mapper.convert(customer, new CustomerDto());
    }
}
