package com.brotherselectronics.orderregistration.repositories;

import com.brotherselectronics.fakers.OrderFaker;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;
    private String userId;

    @BeforeEach
    void setUp() {
        var order = orderRepository.save(new OrderFaker().getEntity());
        this.userId = order.getUserId();
    }

    @Test
    void findByUserId() {
        assertThat(orderRepository.findByUserId(userId)).isNotEmpty();
    }

    @AfterEach
    void tearDown() {
        orderRepository.deleteAll();
    }
}