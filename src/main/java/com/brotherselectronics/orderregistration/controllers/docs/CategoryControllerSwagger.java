package com.brotherselectronics.orderregistration.controllers.docs;


import com.brotherselectronics.orderregistration.domains.enums.Category;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/categories")
@Tag(name = "Category")
public interface CategoryControllerSwagger {

  @GetMapping("/{key}")
  @ResponseStatus(OK)
  @Operation(summary = "Get category by key")
  ResponseEntity<Category> findByKey(@PathVariable("key") String key);

  @GetMapping
  @ResponseStatus(OK)
  @Operation(summary = "Get all categories")
  ResponseEntity<List<Category>> findAll();

}