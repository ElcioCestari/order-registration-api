package com.brotherselectronics.orderregistration.repositories;

import com.brotherselectronics.orderregistration.domains.entities.BaseEntity;
import com.brotherselectronics.orderregistration.domains.entities.Order;
import com.brotherselectronics.orderregistration.domains.entities.Product;
import lombok.Getter;

@Getter
public enum RepositoryDomain {
    PRODUCT(Product.class),
    ORDER(Order.class);

    private final Class<?> clazz;

    RepositoryDomain(Class<? extends BaseEntity> entity) {
        clazz = entity;
    }
}
