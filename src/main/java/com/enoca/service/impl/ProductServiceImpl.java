package com.enoca.service.impl;

import com.enoca.dto.ProductDto;
import com.enoca.entity.Product;
import com.enoca.mapper.MapperUtil;
import com.enoca.repository.ProductRepository;
import com.enoca.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository repository;
    private final MapperUtil mapper;

    @Override
    public List<ProductDto> getAllCustomer() {
        var products = repository.getAllByIsDeleted(false);
        return products.stream()
                .map(product -> mapper.convert(product, new ProductDto()))
                .toList();
    }

    @Override
    public ProductDto getProductById(Long id) {
        Product product = repository.findByIdAndIsDeleted(id,false)
                .orElseThrow(()->new NoSuchElementException("No such product found"));
        return mapper.convert(product, new ProductDto());
    }

    @Override
    public ProductDto create(ProductDto productDto) {
        Product createdProduct = repository.save(mapper.convert(productDto, new Product()));
        return mapper.convert(createdProduct, new ProductDto());
    }
}
