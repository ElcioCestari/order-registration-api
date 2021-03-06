package com.brotherselectronics.orderregistration.utils;

import com.brotherselectronics.orderregistration.domains.entities.BaseEntity;
import com.brotherselectronics.orderregistration.domains.entities.Order;
import com.brotherselectronics.orderregistration.domains.entities.Product;
import com.brotherselectronics.orderregistration.repositories.BaseRepository;
import com.brotherselectronics.orderregistration.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InstancesUtils {

    @Autowired private OrderRepository orderRepository;
    @Autowired private BaseRepository productRepository;

    public BaseRepository getRepository (Class<? extends BaseEntity> clazz) {

        final String order = Order.class.getCanonicalName();
        if (isEquals(clazz, order)) return orderRepository;

        final String product = Product.class.getCanonicalName();
        if (isEquals(clazz, product)) return productRepository;

        throw new IllegalArgumentException("Instance's BaseRepository " + clazz + " not found");
    }

    private boolean isEquals(Class<? extends BaseEntity> clazz, String order) {
        return clazz.getCanonicalName().equals(order);
    }
}