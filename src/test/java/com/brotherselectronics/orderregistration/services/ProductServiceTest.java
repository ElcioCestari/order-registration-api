package com.brotherselectronics.orderregistration.services;

import com.brotherselectronics.fakers.EntityFake;
import com.brotherselectronics.fakers.ProductFaker;
import com.brotherselectronics.orderregistration.domains.dtos.ProductRequestDTO;
import com.brotherselectronics.orderregistration.domains.dtos.ProductResponseDTO;
import com.brotherselectronics.orderregistration.domains.entities.Product;
import com.brotherselectronics.orderregistration.domains.mappers.ProductMapper;
import com.brotherselectronics.orderregistration.exceptions.NotFoundException;
import com.brotherselectronics.orderregistration.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;

import java.util.List;
import java.util.Optional;

import static java.lang.String.valueOf;
import static java.util.Optional.ofNullable;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
class ProductServiceTest {
    private static final String FAIL_MSG = "Must to be throw an exception and don't was threw.";
    private static final EntityFake<Product, ProductRequestDTO, ProductResponseDTO> fake = new ProductFaker();

    private ProductService service;
    @Autowired
    private ProductMapper mapper;
    @Mock
    private ProductRepository repository;

    @BeforeEach
    void setUp() {
        service = new ProductService(mapper, repository);
    }

    @Test
    void findAll_whenHasDataThenReturnNotEmptyList() {
        when(repository.findAll()).thenReturn((List<Product>) fake.getEntityCollection());
        assertThat(service.findAll()).isNotEmpty();
    }

    @Test
    void findAll_whenHasNotDataThenReturnEmptyList() {
        when(repository.findAll()).thenReturn((List<Product>) fake.getEmptyEntityCollection());
        assertThat(service.findAll()).isEmpty();
    }

    @Test
    void findAllPageable_whenHasNotDataThenReturnEmptyList() {
        var pageable = PageRequest.of(1, 2, Sort.by("name"));

        when(repository.findAll(pageable))
                .thenReturn(new PageImpl<>((List<Product>) fake.getEmptyEntityCollection()));

        assertThat(service.findAll(1, 2, "name")).isEmpty();
    }

    @Test
    void findAllPageable_whenHasDataThenReturnNotEmptyList() {
        var pageable = PageRequest.of(1, 2, Sort.by("name"));
        when(repository.findAll(any(Pageable.class)))
                .thenReturn(new PageImpl<>((List<Product>) fake.getEntityCollection(20)));
        Page<ProductResponseDTO> all = service.findAll(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                valueOf(pageable.getSort()));

        assertThat(all).isNotEmpty();
    }

    @Test
    void findById_whenFoundThenReturnResponseDTO() {
        when(repository.findById(anyString())).thenReturn(ofNullable(fake.getEntity()));

        assertEquals(fake.getResponseDTO().getName(), service.findById("any id").getName());
    }

    @Test
    void findById_whenNotFound_thenThrowAnException() {
        when(repository.findById(anyString())).thenReturn(Optional.empty());

        ProductResponseDTO dto = null;
        String anyId = "any id";
        try {
            dto = service.findById(anyId);
            fail(FAIL_MSG);
        } catch (NotFoundException e) {
            assertEquals(e.getMessage(), "Product not found with id: " + anyId);
        }
        assertNull(dto);
    }

    @Test
    void save_whenSuccessful() {
        var fakeRequestDTO = fake.getRequestDTO();
        var fakeEntity = fake.getEntity();

        when(repository.save(any(Product.class))).thenReturn(fakeEntity);

        var productResponseDTO = service.save(fakeRequestDTO);

        assertThat(productResponseDTO).isNotNull();
        assertEquals(productResponseDTO.getName(), fakeRequestDTO.getName());
    }

    @Test
    void update_whenSuccessful() {
        var fakeNewProduct = fake.getRequestDTO();
        var fakeEntity = fake.getEntity();

        when(repository.save(any(Product.class))).thenReturn(fakeEntity);

        var fakeId = "fakeId";
        when(repository.findById(fakeId)).thenReturn(ofNullable(fakeEntity));

        var productResponseDTO = service.update(fakeNewProduct, fakeId);

        assertThat(productResponseDTO).isNotNull();
        assertEquals(productResponseDTO.getName(), fakeNewProduct.getName());
    }

    @Test
    void update_whenFail() {
        ProductRequestDTO fakeNewProduct = fake.getRequestDTO();

        String fakeId = "fakeId";
        when(repository.findById(fakeId)).thenReturn(Optional.empty());
        ProductResponseDTO productResponseDTO = null;
        try {
            productResponseDTO = service.update(fakeNewProduct, fakeId);
            fail(FAIL_MSG);
        } catch (NotFoundException e) {
            assertEquals(e.getMessage(), "Product not found with id: " + fakeId);
        }
        assertThat(productResponseDTO).isNull();
    }

    @Test
    void delete_whenSuccessful() {
        String fakeId = "fakeId";
        when(repository.findById(fakeId)).thenReturn(ofNullable(fake.getEntity()));
        assertDoesNotThrow(() -> service.delete(fakeId));
    }

    @Test
    void delete_whenFail() {
        String fakeId = "fakeId";
        when(repository.findById(fakeId)).thenReturn(Optional.empty());
        try {
            service.delete(fakeId);
            fail(FAIL_MSG);
        } catch (NotFoundException e) {
            assertEquals(e.getMessage(), "Product not found with id: " + fakeId);
        }
    }
}