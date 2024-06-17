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
import java.util.Objects;
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

        if (product.getInStockQuantity()<1){
            throw  new EnocaEcommerceProjectException("Product don't have enough stock");
        }

        // check if product in cart or not
        Optional<CartItemDto> foundCartItem = cart.getCartItems().stream()
                .filter(item->item.getProduct().getId() == productId).findAny();
        if (foundCartItem.isPresent()){
            if (foundCartItem.get().getQuantity()+1 > product.getInStockQuantity()) {
                throw  new EnocaEcommerceProjectException("There is no more stock from this product");
            }
            foundCartItem.get().setQuantity(foundCartItem.get().getQuantity()+1);
            cartItemService.save(foundCartItem.get());
        }else {
            // create new cartItem with product
            CartItemDto cartItem = new CartItemDto();
            cartItem.setProduct(product);
            cartItem.setIsDeleted(false);
            cartItem.setQuantity(1);
            // save new card item
            CartItemDto savedCardItem = cartItemService.saveCartItem(cartItem);
            // add cart item to cart
            cart.getCartItems().add(savedCardItem);
        }

        // increase cart Total price
        cart.setTotalPrice(cart.getTotalPrice().add(product.getPrice()));

        return saveCart(cart);
    }

    @Override
    public CartDto updateCart(long customerId, long productId, int quantity) {
        CartDto cartDto = findByCustomerId(customerId);
        ProductDto productDto = productService.getProductById(productId);
        // check if quantity grater then inStockQuantity
        if (quantity>productDto.getInStockQuantity() || quantity<0){
            throw new EnocaEcommerceProjectException("product quantity couldn't be grater then inStock quantity " +
                    "or less then 0");
        }

        // change the product quantity in cart
        Optional<CartItemDto> foundCartItem = cartDto.getCartItems().stream().
                filter(cartItem -> cartItem.getProduct().getId() == productId)
                .findFirst();
        if (foundCartItem.isPresent()){
            CartItemDto cartItem = foundCartItem.get();
            if (quantity == 0){
                cartItem.setIsDeleted(true);
                cartDto.getCartItems().remove(cartItem);
            }

            // calculate the cart total price and save
            calculateCartTotalPriceAndSave(cartDto, cartItem,quantity);

            //set new quantity and save
            cartItem.setQuantity(quantity);
            cartItemService.save(cartItem);
        }else {
            throw new EnocaEcommerceProjectException("the Cart doesn't have the product");
        }
        return findByCustomerId(customerId);
    }


    @Override
    public CartDto emptyCart(long customerId) {
        CartDto cart = findByCustomerId(customerId);
        // set cart total price 0 and delete all cartItem
        cart.setTotalPrice(BigDecimal.ZERO);
        cart.getCartItems()
                .forEach(item->{
                    item.setIsDeleted(true);
                    cartItemService.saveCartItem(item);
                });
        cart.getCartItems().clear();
        return saveCart(cart);
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

    private CartDto calculateCartTotalPriceAndSave(CartDto cartDto, CartItemDto cartItem, int quantity) {
        int addedQuantity = quantity - cartItem.getQuantity();
        BigDecimal productPrice = cartItem.getProduct().getPrice();
        BigDecimal oldTotalPrice = cartDto.getTotalPrice();
        BigDecimal newTotalPrice = oldTotalPrice.add(productPrice.multiply(BigDecimal.valueOf(addedQuantity)));
        cartDto.setTotalPrice(newTotalPrice);
        return saveCart(cartDto);
    }


    @Override
    @Transactional
    public CartDto removeProductFromCart(Long customerId, Long productId) {
        CartDto cart = findByCustomerId(customerId);
        ProductDto product = productService.getProductById(productId);
        Optional<CartItemDto> foundCartItem = cart.getCartItems().stream().
                filter(cartItem -> Objects.equals(cartItem.getProduct().getId(), productId))
                .findFirst();
        if (foundCartItem.isEmpty()){
            throw new EnocaEcommerceProjectException("the Cart doesn't have the product you want to remove");
        }
        CartItemDto cartItem = foundCartItem.get();
        cart.getCartItems().remove(cartItem);
        cart.setTotalPrice(cart.getTotalPrice().subtract(product.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity()))));
        CartDto afterRemoveCart = saveCart(cart);

        foundCartItem.get().setIsDeleted(true);
        cartItemService.save(foundCartItem.get());

        return afterRemoveCart;
    }




}
