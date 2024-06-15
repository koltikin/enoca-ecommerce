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
        var products = repository.getAllByIsDeletedOrderByProductNameAsc(false);
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

    @Override
    public ProductDto update(ProductDto productDto, Long id) {
        var foundProduct = repository.findByIdAndIsDeleted(id,false);
        if (foundProduct.isPresent()){
            Product productTobeUpdate = mapper.convert(productDto, new Product());
            productTobeUpdate.setId(id);
            Product savedProduct = repository.save(productTobeUpdate);
            return mapper.convert(savedProduct, new ProductDto());
        }else throw new NoSuchElementException("No Product found with Id: " + id );
    }

    @Override
    public ProductDto delete(Long id) {
        var productTobeDelete = repository.findByIdAndIsDeleted(id,false);
        if (productTobeDelete.isPresent()){
            Product deletedProduct = repository.save(productTobeDelete.get());
            deletedProduct.setIsDeleted(true);
            repository.save(deletedProduct);
            return mapper.convert(deletedProduct, new ProductDto());
        }else throw new NoSuchElementException("No Product found with Id: " + id );
    }

    @Override
    public void save(ProductDto product) {
        repository.save(mapper.convert(product,new Product()));
    }
}
