package com.brotherselectronics.orderregistration.utils;

import com.brotherselectronics.orderregistration.domains.entities.BaseEntity;
import com.brotherselectronics.orderregistration.repositories.BaseRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;

@Component
@AllArgsConstructor
public class RepositoryFactory {

    private final List<BaseRepository<? extends BaseEntity, ? extends Serializable>> repositories;

    @SuppressWarnings("unchecked")
    public <T extends BaseEntity> BaseRepository<T, ? extends Serializable> getRepository(Class<T> clazz) {
        return this.repositories.stream()
                .filter(repository -> repository.supports(clazz))
                .map(repository -> (BaseRepository<T, ? extends Serializable>) repository)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No repository found for class: " + clazz.getName()));
    }
}
