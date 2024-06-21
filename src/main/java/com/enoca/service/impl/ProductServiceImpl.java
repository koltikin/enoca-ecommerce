package com.enoca.service.impl;

import com.enoca.dto.ProductDto;
import com.enoca.entity.Product;
import com.enoca.exception.EnocaEcommerceProjectException;
import com.enoca.mapper.MapperUtil;
import com.enoca.repository.ProductRepository;
import com.enoca.service.ProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
                .orElseThrow(()->new EnocaEcommerceProjectException("Product not found with id: " + id));
        return mapper.convert(product, new ProductDto());
    }

    @Override
    @Transactional
    public ProductDto create(ProductDto productDto) {
        Product createdProduct = repository.save(mapper.convert(productDto, new Product()));
        return mapper.convert(createdProduct, new ProductDto());
    }

    @Override
    @Transactional
    public ProductDto update(ProductDto productDto, Long id) {
        var foundProduct = repository.findByIdAndIsDeleted(id,false);
        if (foundProduct.isPresent()){
            Product productTobeUpdate = mapper.convert(productDto, new Product());
            productTobeUpdate.setId(id);
            Product savedProduct = repository.save(productTobeUpdate);
            return mapper.convert(savedProduct, new ProductDto());
        }else throw new EnocaEcommerceProjectException("No Product found with Id: " + id );
    }

    @Override
    @Transactional
    public ProductDto delete(Long id) {
        var productTobeDelete = repository.findByIdAndIsDeleted(id,false);
        if (productTobeDelete.isPresent()){
            Product deletedProduct = repository.save(productTobeDelete.get());
            deletedProduct.setIsDeleted(true);
            repository.save(deletedProduct);
            return mapper.convert(deletedProduct, new ProductDto());
        }else throw new EnocaEcommerceProjectException("No Product found with Id: " + id );
    }

    @Override
    @Transactional
    public void save(ProductDto product) {
        repository.save(mapper.convert(product,new Product()));
    }
}
