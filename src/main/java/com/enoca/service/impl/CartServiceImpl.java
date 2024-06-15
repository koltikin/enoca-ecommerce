package com.enoca.service.impl;

import com.enoca.dto.CartDto;
import com.enoca.dto.OrderItemDto;
import com.enoca.dto.ProductDto;
import com.enoca.entity.Cart;
import com.enoca.exception.EnocaEcommerceProjectException;
import com.enoca.mapper.MapperUtil;
import com.enoca.repository.CartRepository;
import com.enoca.service.CartService;
import com.enoca.service.CustomerService;
import com.enoca.service.ProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;


@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepository repository;
    private final MapperUtil mapper;
    private final ProductService productService;

    @Override
    public List<CartDto> findAllCarts() {
        List<Cart> carts = repository.findAllByIsDeleted(false);
        return carts.stream().map(cart -> mapper.convert(cart, new CartDto()))
                .toList();
    }

    @Override
    public CartDto createCart(CartDto cartDto) {
        Cart savedCart = repository.save(mapper.convert(cartDto, new Cart()));
        return mapper.convert(savedCart, new CartDto());
    }

    @Override
    public CartDto getCart(long id) {
        Cart cart = repository.findByIdAndIsDeleted(id, false)
                .orElseThrow(() -> new EnocaEcommerceProjectException("No cart found with id: " + id));
        return mapper.convert(cart, new CartDto());
    }

    @Override
    public CartDto updateCart(CartDto cartDto, long id) {
        var cart = repository.findByIdAndIsDeleted(id, false);
        if (cart.isPresent()) {
            Cart cartToBeUpdate = mapper.convert(cartDto, new Cart());
            cartToBeUpdate.setId(id);
            return mapper.convert(repository.save(cartToBeUpdate), new CartDto());
        } else throw new EnocaEcommerceProjectException("there is No cart with id: " + id);
    }

    @Override
    public CartDto emptyCart(long id) {
        var cart = repository.findByIdAndIsDeleted(id, false);
        if (cart.isPresent()) {
            Cart cartToBeEmpty = cart.get();
            cartToBeEmpty.setOrderItems(List.of());
            cartToBeEmpty.setTotalPrice(BigDecimal.ZERO);
            return mapper.convert(repository.save(cartToBeEmpty), new CartDto());
        } else throw new EnocaEcommerceProjectException("there is No cart with id: " + id);
    }

    @Override
    public CartDto findByCustomerId(Long customerId) {
        Cart cart = repository.findByCustomerIdAndIsDeleted(customerId,false);
        return mapper.convert(cart, new CartDto());
    }

    @Override
    public void save(CartDto cartDto) {
        repository.save(mapper.convert(cartDto, new Cart()));
    }


    @Override
    @Transactional
    public CartDto addProductToCart (Long customerId, Long productId,int quantity){

        Cart cart = repository.findByCustomerIdAndIsDeleted(customerId,false);
        CartDto cartDto = mapper.convert(cart, new CartDto());

        if (cartDto == null) {
            throw new EnocaEcommerceProjectException("Cart not found for customer id: " + customerId);
        }

        ProductDto productDto = productService.getProductById(productId);

        if (productDto.getInStockQuantity() < quantity) {
            throw new EnocaEcommerceProjectException("Not enough stock for product: " + productDto.getProductName());
        }

        OrderItemDto orderItem = cartDto.getOrderItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElseGet(() -> {
                    OrderItemDto newItem = new OrderItemDto();
                    newItem.setProduct(productDto);
                    newItem.setCart(cartDto);
                    cartDto.getOrderItems().add(newItem);
                    return newItem;
                });

        orderItem.setQuantity(orderItem.getQuantity() + quantity);
        orderItem.setPrice(orderItem.getProduct().getPrice().multiply(BigDecimal.valueOf(orderItem.getQuantity())));
        BigDecimal updatedTotalPrice = calculateCartTotalPrice(cartDto);
        cartDto.setTotalPrice(updatedTotalPrice);
        repository.save(mapper.convert(cartDto, new Cart()));
        return cartDto;
    }

    private BigDecimal calculateCartTotalPrice(CartDto cartDto) {
        return cartDto.getOrderItems().stream()
                .map(OrderItemDto::getPrice)
                .reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
    }


    @Override
    @Transactional
    public CartDto removeProductFromCart(Long customerId, Long productId, int quantity) {

        Cart cart = repository.findByCustomerIdAndIsDeleted(customerId, false);
        CartDto cartDto = mapper.convert(cart, new CartDto());

        OrderItemDto cartItem = cartDto.getOrderItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new EnocaEcommerceProjectException("Product not found in cart for product id: " + productId));

        if (cartItem.getQuantity() < quantity) {
            throw new EnocaEcommerceProjectException("Cannot remove more items than are in the cart for product id: " + productId);
        }

        cartItem.setQuantity(cartItem.getQuantity() - quantity);
        if (cartItem.getQuantity() == 0) {
            cartDto.getOrderItems().remove(cartItem);
        } else {
            cartItem.setPrice(cartItem.getProduct().getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())));
        }

        BigDecimal updatedTotalPrice = calculateCartTotalPrice(cartDto);
        cartDto.setTotalPrice(updatedTotalPrice);
        repository.save(mapper.convert(cartDto, new Cart()));
        return cartDto;
    }




}
