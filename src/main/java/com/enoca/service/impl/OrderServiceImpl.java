package com.enoca.service.impl;

import com.enoca.dto.OrderDto;
import com.enoca.entity.Order;
import com.enoca.mapper.MapperUtil;
import com.enoca.repository.OrderRepository;
import com.enoca.service.CartService;
import com.enoca.service.OrderCodeService;
import com.enoca.service.OrderService;
import com.enoca.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final CartService cartService;
    private final ProductService productService;
    private final OrderRepository repository;
    private final OrderCodeService orderCodeService;
    private final MapperUtil mapper;

    @Override
    public OrderDto placeOrder(Long customerId) {
       return  null;
    }

    @Override
    public OrderDto save(OrderDto order) {
       Order savedOrder = repository.save(mapper.convert(order, new Order()));
       return mapper.convert(savedOrder, new OrderDto());
    }

    @Override
    public OrderDto getOrderForCode(String orderCode) {
        Order order = repository.findOrderByOrderCodeAndIsDeleted(orderCode,false)
                .orElseThrow(()->new NoSuchElementException("No order found with code: " + orderCode));
        return mapper.convert(order, new OrderDto());
    }

    @Override
    public List<OrderDto> getAllOrdersForCustomer(Long customerId) {
        List<Order> orders = repository.findOrderByCustomerIdAndIsDeleted(customerId, false);
        return orders.stream()
                .map(order -> mapper.convert(order, new OrderDto()))
                .toList();
    }
}
