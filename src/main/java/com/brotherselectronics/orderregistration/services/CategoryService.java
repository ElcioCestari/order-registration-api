package com.brotherselectronics.orderregistration.services;

import com.brotherselectronics.orderregistration.domains.enums.Category;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class CategoryService {
    @Cacheable("categoryFindByKey")
    public Category findByKey(String key) {
        return Category.valueOf(key);
    }

    @Cacheable("categoryFindAll")
    public List<Category> findAll() {
        return Arrays.stream(Category.values()).toList();
    }
}
