package com.brotherselectronics.orderregistration.services;

import com.brotherselectronics.orderregistration.domains.dtos.ProductRequestDTO;
import com.brotherselectronics.orderregistration.domains.dtos.ProductResponseDTO;
import com.brotherselectronics.orderregistration.domains.entities.Product;
import com.brotherselectronics.orderregistration.domains.mappers.ProductMapper;
import com.brotherselectronics.orderregistration.exceptions.NotFoundException;
import com.brotherselectronics.orderregistration.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService implements BaseService<ProductRequestDTO, ProductResponseDTO, Product> {

    private final ProductMapper mapper;
    private final ProductRepository productRepository;

    @Cacheable("productFindAll")
    public Page<ProductResponseDTO> findAll(final Integer page, final Integer size, final String... sort) {
        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
            Page<Product> productPage = productRepository.findAll(pageable);
            return new PageImpl<>(mapper.toDtoResponseList(
                    productPage.toList()),
                    pageable,
                    productPage.getTotalElements()
            );
        } catch (Exception e) {
            log.error("Error while fetching products", e);
            throw new NotFoundException();
        }

    }

    @Override
    @Cacheable("productFindAll")
    public List<ProductResponseDTO> findAll() {
        List<Product> productList = productRepository.findAll();
        return mapper.toDtoResponseList(productList);
    }

    @Override
    @Cacheable(cacheNames = "products", key = "#id")
    public ProductResponseDTO findById(String id) {
        Product product = getProductFromRepositoryOrThrowNotFoundException(id);
        return mapper.toDtoResponse(product);
    }

    @Override
    @CacheEvict(allEntries = true, cacheNames = "productFindAll")
    public ProductResponseDTO save(ProductRequestDTO dto) {
        Product product = mapperToProduct(dto);
        productRepository.save(product);
        return mapper.toDtoResponse(product);
    }

    @Override
    @CachePut(cacheNames = "products", key = "#id")
    @CacheEvict(allEntries = true, cacheNames = "productFindAll")
    public ProductResponseDTO update(ProductRequestDTO dto, String id) {
        Product productSaved = this.getProductFromRepositoryOrThrowNotFoundException(id);
        Product productToMerge = mapperToProduct(dto);
        merge(productToMerge, productSaved);
        productRepository.save(productSaved);
        return mapper.toDtoResponse(productSaved);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(allEntries = true, cacheNames = "productFindAll"),
            @CacheEvict(cacheNames = "products", key = "#id")
    })
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

    @CachePut(cacheNames = "products", key = "#id")
    @CacheEvict(allEntries = true, cacheNames = "productFindAll")
    public ProductResponseDTO patch(ProductRequestDTO dto, String id) {
        Product product = getProductFromRepositoryOrThrowNotFoundException(id);
        mapper.merge(dto, product);
        productRepository.save(product);
        return mapper.toDtoResponse(product);
    }
}