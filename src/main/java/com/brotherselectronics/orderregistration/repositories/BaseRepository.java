package com.brotherselectronics.orderregistration.repositories;

import com.brotherselectronics.orderregistration.domains.entities.BaseEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BaseRepository<T extends BaseEntity, ID> extends MongoRepository<T,ID> {
}