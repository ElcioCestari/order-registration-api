package com.brotherselectronics.orderregistration.repositories;

import com.brotherselectronics.fakers.OrderFaker;
import com.brotherselectronics.orderregistration.configs.MongoConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
@ExtendWith(SpringExtension.class)
@Import(MongoConfig.class)
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
        var list = orderRepository.findByUserId(this.userId);
        orderRepository.deleteAll(list);
    }
}