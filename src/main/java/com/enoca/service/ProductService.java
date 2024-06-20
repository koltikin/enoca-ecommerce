package com.enoca.service;

import com.enoca.dto.ProductDto;

import java.util.List;

public interface ProductService {
    List<ProductDto> getAllCustomer();

    ProductDto getProductById(Long id);

    ProductDto create(ProductDto productDto);

    ProductDto update(ProductDto productDto, Long id);

    ProductDto delete(Long id);

    void save(ProductDto product);
}
