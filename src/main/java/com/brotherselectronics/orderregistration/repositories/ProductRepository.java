package com.brotherselectronics.orderregistration.repositories;

import com.brotherselectronics.orderregistration.domains.entities.Product;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends BaseRepository<Product, String> {
}