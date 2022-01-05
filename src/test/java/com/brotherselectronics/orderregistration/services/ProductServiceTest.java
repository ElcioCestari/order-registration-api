package com.brotherselectronics.orderregistration.services;

import com.brotherselectronics.fakers.EntityFake;
import com.brotherselectronics.fakers.ProductFaker;
import com.brotherselectronics.orderregistration.domains.dtos.ProductRequestDTO;
import com.brotherselectronics.orderregistration.domains.dtos.ProductResponseDTO;
import com.brotherselectronics.orderregistration.domains.entities.Product;
import com.brotherselectronics.orderregistration.domains.mappers.ProductMapper;
import com.brotherselectronics.orderregistration.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class ProductServiceTest {

    private static final EntityFake<Product, ProductRequestDTO, ProductResponseDTO> fake = new ProductFaker();

    @InjectMocks private ProductService service;
    @Mock private ProductMapper mapper;
    @Mock private ProductRepository repository;

    @BeforeEach
    void setUp() {
    }

    @Test
    void findAll_whenHasDataThenReturnANotEmptyList() {

        List<Product> productList = (List<Product>) fake.getEntityCollection();
        when(repository.findAll()).thenReturn(productList);

        var dtoList = (List<ProductResponseDTO>) fake.getResponseDTOCollection();
        when(mapper.toDtoResponseList(productList)).thenReturn(dtoList);

        List<?> productResponseDTOList = service.findAll();
        assertThat(productResponseDTOList).isNotEmpty();
    }

    @Test
    void findAll_whenHasNotDataThenReturnAEmptyList() {
        var productList = (List<Product>) fake.getEmptyEntityCollection();
        when(repository.findAll()).thenReturn(productList);

        var dtoList = (List<ProductResponseDTO>) fake.getEmptyResponseDTOCollection();
        when(mapper.toDtoResponseList(productList)).thenReturn(dtoList);

        List<?> productResponseDTOList = service.findAll();
        assertThat(productResponseDTOList).isEmpty();
    }

    @Test
    void findById() {
        Product entity = fake.getEntity();
        when(repository.findById(anyString())).thenReturn(Optional.ofNullable(entity));

        ProductResponseDTO responseDTO = fake.getResponseDTO();
        when(mapper.toDtoResponse(any(Product.class))).thenReturn(responseDTO);

        ProductResponseDTO dto = service.findById("any id");
        assertEquals(responseDTO,dto);
    }

    @Test
    void save() {
    }

    @Test
    void update() {

    }

    @Test
    void delete() {
    }
}