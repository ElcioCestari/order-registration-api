package com.brotherselectronics.orderregistration.services;

import com.brotherselectronics.fakers.EntityFake;
import com.brotherselectronics.fakers.ProductFaker;
import com.brotherselectronics.orderregistration.domains.dtos.ProductResponseDTO;
import com.brotherselectronics.orderregistration.domains.entities.Product;
import com.brotherselectronics.orderregistration.domains.mappers.ProductMapper;
import com.brotherselectronics.orderregistration.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

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
        Mockito.when(repository.findAll()).thenReturn(list);
        Mockito.when(mapper.toDtoResponseList(list)).thenReturn((List<ProductResponseDTO>) fake.getResponseDTOCollection());
        List<?> dtoList = service.findAll();
        assertThat(dtoList).isNotEmpty();
    }

    @Test
    void findById() {
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