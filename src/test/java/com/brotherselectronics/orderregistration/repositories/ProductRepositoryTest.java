package com.brotherselectronics.orderregistration.repositories;

import com.brotherselectronics.orderregistration.domains.entities.Product;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.data.domain.Sort.by;

@DataMongoTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository repository;

    @Test
    void findAll_whenSuccessful_thenReturnAList() {
        assertThat(repository.findAll()).isNotEmpty();
    }

    @Test
    void findAllPageable_whenSuccessful_thenReturnAList() {
        var pageable = PageRequest.of(3, 2, by("name"));
        Page<Product> all = repository.findAll(pageable);
        assertThat(all).isNotEmpty();
        assertThat(all.getContent()).hasSizeLessThanOrEqualTo(2);
    }

    @Test
    void findAllCustom_whenSuccessful_thenReturnAList() {
        var allPriceProduct = repository.findPriceProduct();
        assertThat(allPriceProduct.get(0).getCategoryName()).isNotNull();
    }
}