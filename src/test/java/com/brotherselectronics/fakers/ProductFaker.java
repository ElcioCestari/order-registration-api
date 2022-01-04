package com.brotherselectronics.fakers;

import com.brotherselectronics.orderregistration.domains.dtos.ProductRequestDTO;
import com.brotherselectronics.orderregistration.domains.dtos.ProductResponseDTO;
import com.brotherselectronics.orderregistration.domains.entities.BaseEntity;
import com.brotherselectronics.orderregistration.domains.entities.Product;
import com.brotherselectronics.orderregistration.domains.entities.Stock;
import com.brotherselectronics.orderregistration.domains.enums.Category;
import com.github.javafaker.Faker;
import org.apache.commons.lang3.RandomUtils;

public class ProductFaker extends BaseEntity implements EntityFake<Product, ProductRequestDTO, ProductResponseDTO>{

    private static final Faker FAKER = new Faker();

    @Override
    public Product getEntity() {
        return Product.builder()
                .name(FAKER.commerce().productName())
                .category(Category.ELETRONIC)
                .description(FAKER.lorem().characters())
                .haveInStock(String.valueOf(RandomUtils.nextBoolean()))
                .stock(getStock())
                .build();
    }

    @Override
    public ProductRequestDTO getRequestDTO() {
        return ProductRequestDTO.builder()
                .name(FAKER.commerce().productName())
                .category(Category.ELETRONIC)
                .description(FAKER.lorem().characters())
                .haveInStock(String.valueOf(RandomUtils.nextBoolean()))
                .stock(getStock())
                .build();
    }

    @Override
    public ProductResponseDTO getResponseDTO() {
        return ProductResponseDTO.builder()
                .name(FAKER.commerce().productName())
                .category(Category.ELETRONIC)
                .description(FAKER.lorem().characters())
                .haveInStock(String.valueOf(RandomUtils.nextBoolean()))
                .stock(getStock())
                .build();
    }

    private Stock getStock() {
        return new Stock(RandomUtils.nextInt());
    }
}