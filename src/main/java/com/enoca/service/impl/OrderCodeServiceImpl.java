package com.enoca.service.impl;

import com.enoca.entity.OrderCode;
import com.enoca.repository.OrderCodRepository;
import com.enoca.service.OrderCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderCodeServiceImpl implements OrderCodeService {
    private final OrderCodRepository orderCodRepository;

    @Override
    public String generateOrderCode() {
        OrderCode orderCode = orderCodRepository.findById(1L).orElseGet(()-> {
            OrderCode newOrderCode = new OrderCode();
            newOrderCode.setOrderCodeValue(0L);
            return newOrderCode;
        });

        Long newOrderCodeValue = orderCode.getOrderCodeValue() + 1; // increase the orderCodeValue by 1
        orderCode.setOrderCodeValue(newOrderCodeValue); // update the orderCodeValue
        orderCodRepository.save(orderCode); // saved the orderCode object without change the id
        return String.format("enoca-%05d", newOrderCodeValue); // return the orderCode with format 'enoca-00001'

    }
}
