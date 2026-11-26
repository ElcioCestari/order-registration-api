package com.brotherselectronics.orderregistration.utils;

import com.brotherselectronics.orderregistration.domains.entities.BaseEntity;
import com.brotherselectronics.orderregistration.domains.entities.Order;
import com.brotherselectronics.orderregistration.domains.entities.Product;
import com.brotherselectronics.orderregistration.domains.entities.SystemUser;
import com.brotherselectronics.orderregistration.repositories.BaseRepository;
import com.brotherselectronics.orderregistration.repositories.OrderRepository;
import com.brotherselectronics.orderregistration.repositories.ProductRepository;
import com.brotherselectronics.orderregistration.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class InstancesUtils {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public BaseRepository getRepository(Class<? extends BaseEntity> clazz) {
        if (Order.class.equals(clazz)) {
            return orderRepository;
        }
        if (Product.class.equals(clazz)) {
            return productRepository;
        }
        if (SystemUser.class.equals(clazz)) {
            return userRepository;
        }
        throw new IllegalArgumentException("Instance's BaseRepository " + clazz + " not found");
    }
}
