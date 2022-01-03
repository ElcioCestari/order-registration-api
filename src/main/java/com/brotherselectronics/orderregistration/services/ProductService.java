package com.brotherselectronics.orderregistration.services;

import com.brotherselectronics.orderregistration.domains.dtos.ProductRequestDTO;
import com.brotherselectronics.orderregistration.domains.dtos.ProductResponseDTO;
import com.brotherselectronics.orderregistration.domains.entities.Product;
import com.brotherselectronics.orderregistration.domains.mappers.ProductMapper;
import com.brotherselectronics.orderregistration.exceptions.NotFoundException;
import com.brotherselectronics.orderregistration.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService implements IBaseService<ProductRequestDTO, ProductResponseDTO, Product>{

    private final ProductMapper mapper;
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductMapper mapper, ProductRepository productRepository) {
        this.mapper = mapper;
        this.productRepository = productRepository;
    }

    @Override
    public List<ProductResponseDTO> findAll() {
        List<Product> productList = productRepository.findAll();
        return mapper.toDtoResponseList(productList);
    }

    @Override
    public ProductResponseDTO findById(String id) {
        Product product = getProductFromRepositoryOrThrowNotFoundException(id);
        return mapperToResponseDTO(product);
    }

    @Override
    public ProductResponseDTO save(ProductRequestDTO dto) {
        Product product = mapperToProduct(dto);
        productRepository.save(product);
        return mapperToResponseDTO(product);
    }

    @Override
    public ProductResponseDTO update(ProductRequestDTO dto, String id) {
        Product productSaved = this.getProductFromRepositoryOrThrowNotFoundException(id);
        Product productToSave = mapperToProduct(dto);
        merge(productToSave, productSaved);
        productRepository.save(productToSave);
        return mapperToResponseDTO(productToSave);
    }

    @Override
    public void delete(String id) {
        this.getProductFromRepositoryOrThrowNotFoundException(id);
        productRepository.deleteById(id);
    }

    private Product mapperToProduct(ProductRequestDTO dto) {
        return (Product) mapper.toEntity(dto);
    }

    private ProductResponseDTO mapperToResponseDTO(Product product) {
        return (ProductResponseDTO) mapper.toDtoResponse(product);
    }

    private Product getProductFromRepositoryOrThrowNotFoundException(String id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found with id: " + id));
    }
}