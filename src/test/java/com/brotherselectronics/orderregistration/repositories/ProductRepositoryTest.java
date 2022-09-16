package com.brotherselectronics.orderregistration.repositories;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

@DataMongoTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository repository;

    @Test
    void findAll_whenSuccessful_thenReturnAList() {
        Assertions.assertThat(repository.findAll()).isNotEmpty();
    }

    @Test
    void findAllCustom_whenSuccessful_thenReturnAList() {
        var allPriceProduct = repository.findPriceProduct();
        Assertions.assertThat(allPriceProduct.get(0).getCategoryName()).isNotNull();
    }
}