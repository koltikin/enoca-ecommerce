package com.enoca.service.impl;

import com.enoca.dto.CartDto;
import com.enoca.dto.CartItemDto;
import com.enoca.dto.ProductDto;
import com.enoca.entity.Cart;
import com.enoca.exception.EnocaEcommerceProjectException;
import com.enoca.mapper.MapperUtil;
import com.enoca.repository.CartRepository;
import com.enoca.service.CartItemService;
import com.enoca.service.CartService;
import com.enoca.service.ProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepository repository;
    private final MapperUtil mapper;
    private final ProductService productService;
    private final CartItemService cartItemService;

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
    public CartDto updateCart(long cartId, long productId, int quantity) {
        CartDto cartDto = getCart(cartId);
        ProductDto productDto = productService.getProductById(productId);
        // check if quantity grater then inStockQuantity
        if (quantity>productDto.getInStockQuantity()){
            throw new EnocaEcommerceProjectException("product quantity couldn't be grater then inStock quantity");
        }
        // change the product quantity in cart
        Optional<CartItemDto> foundCartItem = cartDto.getCartItems().stream().
                filter(cartItem -> cartItem.getProduct().getId() == productId)
                .findFirst();
        if (foundCartItem.isPresent()){
            foundCartItem.get().setQuantity(quantity);
            cartItemService.save(foundCartItem.get());
        }else {
            throw new EnocaEcommerceProjectException("the Cart doesn't have the product");
        }
        return getCart(cartId);
    }


    @Override
    public CartDto emptyCart(long id) {
        return null;
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

        return null;
    }

    private BigDecimal calculateCartTotalPrice(CartDto cartDto) {
        return null;
//        return cartDto.getCartItems().stream()
//                .map(OrderItemDto::getPrice)
//                .reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
    }


    @Override
    @Transactional
    public CartDto removeProductFromCart(Long customerId, Long productId, int quantity) {
        return null;
    }




}
