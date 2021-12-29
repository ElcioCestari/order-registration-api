package com.brotherselectronics.orderregistration.repositories;

import com.brotherselectronics.orderregistration.domains.entities.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderRepository extends MongoRepository<Order,String> {
}