package com.brotherselectronics.orderregistration.services;

import com.brotherselectronics.fakers.EntityFake;
import com.brotherselectronics.fakers.ProductFaker;
import com.brotherselectronics.orderregistration.domains.dtos.ProductRequestDTO;
import com.brotherselectronics.orderregistration.domains.dtos.ProductResponseDTO;
import com.brotherselectronics.orderregistration.domains.entities.Product;
import com.brotherselectronics.orderregistration.domains.mappers.ProductMapper;
import com.brotherselectronics.orderregistration.repositories.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class ProductServiceTest {

    private static final EntityFake fake = new ProductFaker();

    @InjectMocks private ProductService service;
    @Mock private ProductMapper mapper;
    @Mock private ProductRepository repository;

    @BeforeEach
    void setUp() {
    }

    @Test
    void findAll() {
        List<Product> list = (List<Product>) fake.getEntityCollection();
        when(repository.findAll()).thenReturn(list);
        when(mapper.toDtoResponseList(list)).thenReturn((List<ProductResponseDTO>) fake.getResponseDTOCollection());
        List<?> dtoList = service.findAll();
        assertThat(dtoList).isNotEmpty();
    }

    @Test
    void findById() {
        Product entity = (Product) fake.getEntity();
        when(repository.findById(anyString())).thenReturn(Optional.ofNullable(entity));

        ProductResponseDTO responseDTO = (ProductResponseDTO) fake.getResponseDTO();
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