package com.enoca.service.impl;

import com.enoca.dto.*;
import com.enoca.entity.Order;
import com.enoca.exception.EnocaEcommerceProjectException;
import com.enoca.mapper.MapperUtil;
import com.enoca.repository.OrderRepository;
import com.enoca.service.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final CartService cartService;
    private final OrderRepository repository;
    private final OrderCodeService orderCodeService;
    private final OrderItemService orderItemService;
    private final ProductService productService;
    private final MapperUtil mapper;

    @Override
    @Transactional
    public OrderDto placeOrder(Long customerId) {
        CartDto cart = cartService.findByCustomerId(customerId);

        // check if customer has product in cart
        if (cart.getCartItems().isEmpty()){
            throw new EnocaEcommerceProjectException("There is no product in customer cart");
        }
        // create new order
        OrderDto order = new OrderDto();
        // create orderItems list and add order item
        List<OrderItemDto> orderItems = cart.getCartItems().stream()
                .map(item->{
                    OrderItemDto orderItem = new OrderItemDto();
                    ProductDto product = item.getProduct();

                    orderItem.setProduct(product);
                    orderItem.setPrice(product.getPrice());
                    orderItem.setQuantity(item.getQuantity());
                    // decrease product quantity
                    if (product.getInStockQuantity() < item.getQuantity()){
                        throw new EnocaEcommerceProjectException("The product: '" + product.getProductName() + "'"
                                + " doesn't have enough stock");
                    }
                    product.setInStockQuantity(product.getInStockQuantity() - item.getQuantity());
                    productService.save(product);
                    orderItemService.saveOrderItem(orderItem);
                    return orderItem;
                }).toList();
        order.setOrderItems(orderItems);
        order.setTotalPrice(cart.getTotalPrice());
        order.setOrderCode(orderCodeService.generateOrderCode());
        order.setCustomer(cart.getCustomer());
        order.setOrderDateTime(LocalDateTime.now());
        // clear cartItems
        cartService.emptyCart(customerId);
        Order savedOrder = repository.save(mapper.convert(order, new Order()));
        return mapper.convert(savedOrder, new OrderDto());

    }

    @Override
    @Transactional
    public OrderDto save(OrderDto order) {
       Order savedOrder = repository.save(mapper.convert(order, new Order()));
       return mapper.convert(savedOrder, new OrderDto());
    }

    @Override
    public OrderDto getOrderForCode(String orderCode) {
        Order order = repository.findOrderByOrderCodeAndIsDeleted(orderCode,false)
                .orElseThrow(()->new EnocaEcommerceProjectException("No order found with code: " + orderCode));
        return mapper.convert(order, new OrderDto());
    }

    @Override
    public List<OrderDto> getAllOrdersForCustomer(Long customerId) {
        List<Order> orders = repository.findOrderByCustomerIdAndIsDeleted(customerId, false);
        return orders.stream()
                .map(order -> mapper.convert(order, new OrderDto()))
                .toList();
    }

    @Override
    public List<OrderDto> getAllOrders() {
        List<Order> orders = repository.findAllByIsDeleted(false);
        return orders.stream()
                .map(order->mapper.convert(order, new OrderDto()))
                .toList();
    }
}
