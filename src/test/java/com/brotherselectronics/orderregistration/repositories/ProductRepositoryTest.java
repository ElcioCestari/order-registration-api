package com.brotherselectronics.orderregistration.repositories;

import com.brotherselectronics.fakers.ProductFaker;
import com.brotherselectronics.orderregistration.configs.MongoConfig;
import com.brotherselectronics.orderregistration.domains.entities.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.validation.ConstraintViolationException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.springframework.data.domain.Sort.by;

@DataMongoTest
@ExtendWith(SpringExtension.class)
@Import(MongoConfig.class)
class ProductRepositoryTest {

    @Autowired
    private ProductRepository repository;

    @Test
    void save() {
        Product entity = new ProductFaker().getEntity();
        Product save = this.repository.save(entity);
        assertThat(save).isNotNull();
    }

    @Test
    void save_whenUserPasswordEmptyThenShouldNotBeSaved() {
        var entity = Product.builder().build();
        entity.setName("");
        assertThatThrownBy(() -> repository.save(entity)).isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    void findAll_whenSuccessful_thenReturnList() {
        assertThat(repository.findAll()).isNotEmpty();
    }

    @Test
    void findAllPageable_whenSuccessful_thenReturnList() {
        var pageable = PageRequest.of(3, 2, by("name"));
        Page<Product> all = repository.findAll(pageable);
        assertThat(all).isNotEmpty();
        assertThat(all.getContent()).hasSizeLessThanOrEqualTo(2);
    }

    @Test
    void findAllCustom_whenSuccessful_thenReturnList() {
        var allPriceProduct = repository.findPriceProduct();
        assertThat(allPriceProduct.get(0).getCategoryName()).isNotNull();
    }
}