package com.brotherselectronics.orderregistration.controllers;


import com.brotherselectronics.orderregistration.domains.dtos.ProductRequestDTO;
import com.brotherselectronics.orderregistration.domains.dtos.ProductResponseDTO;
import com.brotherselectronics.orderregistration.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@Validated
public class ProductController {
    private final ProductService service;

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> findById(@PathVariable("id") String id) {
        return ok(service.findById(id));
    }

    @GetMapping()
    public ResponseEntity<List<ProductResponseDTO>> findAll(@RequestParam(required = false, defaultValue = "10")
                                                            @Valid
                                                            @Min(1) int size,
                                                            @RequestParam(required = false, defaultValue = "0")
                                                            @Valid
                                                            @Min(0) int page,
                                                            @RequestParam(required = false, defaultValue = "name")
                                                            @Valid
                                                            @NotNull String[] sort) {
        return ok(service.findAll(page, size, sort));
    }

    @PostMapping
    public ResponseEntity<ProductResponseDTO> save(@Valid @RequestBody ProductRequestDTO dto) {
        return status(CREATED).body(service.save(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> update(@Valid @RequestBody ProductRequestDTO dto,
                                                     @PathVariable String id) {
        return ok(service.update(dto, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.delete(id);
        return status(NO_CONTENT).build();
    }

}