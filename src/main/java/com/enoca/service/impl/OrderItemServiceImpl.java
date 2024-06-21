package com.enoca.service.impl;

import com.enoca.dto.OrderItemDto;
import com.enoca.entity.OrderItem;
import com.enoca.mapper.MapperUtil;
import com.enoca.repository.OrderItemRepository;
import com.enoca.service.OrderItemService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {
    private final OrderItemRepository orderItemRepository;
    private final MapperUtil mapper;
    @Override
    @Transactional
    public OrderItemDto saveOrderItem(OrderItemDto orderItem) {
        OrderItem savedOrderItem = orderItemRepository.save(mapper.convert(orderItem, new OrderItem()));
        return mapper.convert(savedOrderItem, new OrderItemDto());
    }
}
