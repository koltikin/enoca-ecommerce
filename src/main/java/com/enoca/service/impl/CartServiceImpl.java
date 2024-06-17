package com.enoca.service.impl;

import com.enoca.dto.CartDto;
import com.enoca.dto.CartItemDto;
import com.enoca.dto.ProductDto;
import com.enoca.entity.Cart;
import com.enoca.entity.CartItem;
import com.enoca.exception.EnocaEcommerceProjectException;
import com.enoca.mapper.MapperUtil;
import com.enoca.repository.CartRepository;
import com.enoca.service.CartItemService;
import com.enoca.service.CartService;
import com.enoca.service.CustomerService;
import com.enoca.service.ProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
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
    @Transactional
    public CartDto addProductToCart (long customerId, long productId){
        CartDto cart = findByCustomerId(customerId);
        ProductDto product = productService.getProductById(productId);
        // create new cartItem with product
        CartItemDto cartItem = new CartItemDto();
        cartItem.setProduct(product);
        cartItem.setQuantity(1);
        // save new card item
        CartItemDto savedCardItem = cartItemService.saveCartItem(cartItem);
        // add cart item to cart
        cart.getCartItems().add(savedCardItem);

        // increase cart Total price
        cart.setTotalPrice(cart.getTotalPrice().add(product.getPrice()));

        return saveCart(cart);
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
            CartItemDto cartItem = foundCartItem.get();

            // calculate the cart total price and save
            calculateCartTotalPriceAndSave(cartDto, cartItem,quantity);

            //set new quantity and save
            cartItem.setQuantity(quantity);
            cartItemService.save(cartItem);
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
        Cart cart = repository.findByCustomerIdAndIsDeleted(customerId,false)
                .orElseThrow(()->new EnocaEcommerceProjectException("No specific cart found"));
        return mapper.convert(cart, new CartDto());
    }

    @Override
    public CartDto saveCart(CartDto cartDto) {
        Cart savedCart = repository.save(mapper.convert(cartDto, new Cart()));
        return mapper.convert(savedCart, new CartDto());
    }

    private void calculateCartTotalPriceAndSave(CartDto cartDto, CartItemDto cartItem, int quantity) {
        int addedQuantity = quantity - cartItem.getQuantity();
        BigDecimal productPrice = cartItem.getProduct().getPrice();
        BigDecimal oldTotalPrice = cartDto.getTotalPrice();
        BigDecimal newTotalPrice = oldTotalPrice.add(productPrice.multiply(BigDecimal.valueOf(addedQuantity)));
        cartDto.setTotalPrice(newTotalPrice);
        saveCart(cartDto);
    }


    @Override
    @Transactional
    public CartDto removeProductFromCart(Long customerId, Long productId, int quantity) {
        return null;
    }




}
