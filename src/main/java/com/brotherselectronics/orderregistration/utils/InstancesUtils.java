package com.brotherselectronics.orderregistration.utils;

import com.brotherselectronics.orderregistration.domains.entities.BaseEntity;
import com.brotherselectronics.orderregistration.domains.entities.BaseEntityImp;
import com.brotherselectronics.orderregistration.domains.entities.Order;
import com.brotherselectronics.orderregistration.domains.entities.Product;
import com.brotherselectronics.orderregistration.repositories.BaseRepository;
import com.brotherselectronics.orderregistration.repositories.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class InstancesUtils {

    private final OrderRepository orderRepository;
    private final BaseRepository productRepository;

    public BaseRepository getRepository(Class<? extends BaseEntity> clazz) {

        final String order = Order.class.getCanonicalName();
        if (isEquals((Class<? extends BaseEntityImp>) clazz, order)) {
            return orderRepository;
        }

        final String product = Product.class.getCanonicalName();
        if (isEquals((Class<? extends BaseEntityImp>) clazz, product)) {
            return productRepository;
        }

        throw new IllegalArgumentException("Instance's BaseRepository " + clazz + " not found");
    }

    private boolean isEquals(Class<? extends BaseEntityImp> clazz, String order) {
        return clazz.getCanonicalName().equals(order);
    }
}