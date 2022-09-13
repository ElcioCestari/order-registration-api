package com.brotherselectronics.orderregistration.controllers;


import com.brotherselectronics.orderregistration.domains.enums.Category;
import com.brotherselectronics.orderregistration.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService service;

    @GetMapping("/{key}")
    public ResponseEntity<Category> findByKey(@PathVariable("key") String key) {
        return ok(service.findByKey(key));
    }

    @GetMapping
    public ResponseEntity<List<Category>> findAll() {
        return ok(service.findAll());
    }

}