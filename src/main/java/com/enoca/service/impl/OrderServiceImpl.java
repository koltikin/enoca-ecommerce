package com.enoca.service.impl;

import com.enoca.dto.CartDto;
import com.enoca.dto.OrderDto;
import com.enoca.dto.OrderItemDto;
import com.enoca.dto.ProductDto;
import com.enoca.entity.Order;
import com.enoca.mapper.MapperUtil;
import com.enoca.repository.OrderRepository;
import com.enoca.service.CartService;
import com.enoca.service.OrderCodeService;
import com.enoca.service.OrderService;
import com.enoca.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

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
        CartDto cart = cartService.findByCustomerId(customerId);
        if (cart == null || cart.getOrderItems().isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        OrderDto order = new OrderDto();
        order.setCustomer(cart.getCustomer());
        order.setItems(cart.getOrderItems().stream().map(cartItem -> {
            OrderItemDto orderItem = new OrderItemDto();
            orderItem.setOrder(order);
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(cartItem.getPrice());
            return orderItem;
        }).collect(Collectors.toList()));
        order.setTotalPrice(cart.getTotalPrice());

        // update the stock
        for (OrderItemDto cartItem : cart.getOrderItems()) {
            ProductDto product = cartItem.getProduct();
            product.setInStockQuantity(product.getInStockQuantity() - cartItem.getQuantity());
            productService.save(product);
        }

        // Generate a unique orderCode
        String orderCode = orderCodeService.generateOrderCode();
        order.setOrderCode(orderCode);

        // clear the cart
        cart.getOrderItems().clear();
        cart.setTotalPrice(BigDecimal.ZERO);
        cartService.save(cart);

        Order savedOrder = repository.save(mapper.convert(order,new Order()));

        return mapper.convert(savedOrder, new OrderDto());
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
