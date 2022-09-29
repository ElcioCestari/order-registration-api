package com.brotherselectronics.orderregistration.services;

import com.brotherselectronics.orderregistration.domains.dtos.ProductRequestDTO;
import com.brotherselectronics.orderregistration.domains.dtos.ProductResponseDTO;
import com.brotherselectronics.orderregistration.domains.entities.Product;
import com.brotherselectronics.orderregistration.domains.mappers.ProductMapper;
import com.brotherselectronics.orderregistration.exceptions.NotFoundException;
import com.brotherselectronics.orderregistration.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService implements IBaseService<ProductRequestDTO, ProductResponseDTO, Product> {

    private final ProductMapper mapper;
    private final ProductRepository productRepository;

    @Cacheable("productFindAll")
    public Page<ProductResponseDTO> findAll(final Integer page, final Integer size, final String... sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        Page<Product> productPage = productRepository.findAll(pageable);
        return new PageImpl<>(mapper.toDtoResponseList(
                productPage.toList()),
                pageable,
                productPage.getTotalElements()
        );
    }

    @Override
    @Cacheable("productFindAll")
    public List<ProductResponseDTO> findAll() {
        List<Product> productList = productRepository.findAll();
        return mapper.toDtoResponseList(productList);
    }

    @Override
    public ProductResponseDTO findById(String id) {
        Product product = getProductFromRepositoryOrThrowNotFoundException(id);
        return mapper.toDtoResponse(product);
    }

    @CacheEvict(allEntries = true, cacheNames = "productFindAll")
    @Override
    public ProductResponseDTO save(ProductRequestDTO dto) {
        Product product = mapperToProduct(dto);
        productRepository.save(product);
        return mapper.toDtoResponse(product);
    }

    @CacheEvict(allEntries = true, cacheNames = "productFindAll")
    @Override
    public ProductResponseDTO update(ProductRequestDTO dto, String id) {
        Product productSaved = this.getProductFromRepositoryOrThrowNotFoundException(id);
        Product productToMerge = mapperToProduct(dto);
        merge(productToMerge, productSaved);
        productRepository.save(productSaved);
        return mapper.toDtoResponse(productSaved);
    }

    @CacheEvict(allEntries = true, cacheNames = "productFindAll")
    @Override
    public void delete(String id) {
        this.getProductFromRepositoryOrThrowNotFoundException(id);
        productRepository.deleteById(id);
    }

    private Product mapperToProduct(ProductRequestDTO dto) {
        return mapper.toEntity(dto);
    }

    private Product getProductFromRepositoryOrThrowNotFoundException(String id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found with id: " + id));
    }
}