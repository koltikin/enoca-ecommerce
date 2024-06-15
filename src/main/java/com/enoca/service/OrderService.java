package com.enoca.service;


import com.enoca.dto.OrderDto;

import java.util.List;

public interface OrderService {
    OrderDto placeOrder(Long customerId);

    OrderDto save(OrderDto order);

    OrderDto getOrderForCode(Long orderCode);

    List<OrderDto> getAllOrdersForCustomer(Long customerId);
}