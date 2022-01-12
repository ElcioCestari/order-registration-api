package com.brotherselectronics.orderregistration.repositories;

import com.brotherselectronics.orderregistration.domains.entities.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.util.List;

@DataMongoTest
class OrderRepositoryTest {

    @Autowired private OrderRepository orderRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    void findByUserId() {
        List<Order> orders = orderRepository.findByUserId("61d4afeb2c9551533eb2e74a");
//        Assertions.assertThat(orders).isNotEmpty();
    }
}