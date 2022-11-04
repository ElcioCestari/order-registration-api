package com.brotherselectronics.orderregistration.controllers.docs;


import com.brotherselectronics.orderregistration.domains.dtos.ProductRequestDTO;
import com.brotherselectronics.orderregistration.domains.dtos.ProductResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/product")
@Tag(name = "Product")
public interface ProductControllerSwagger {

  @GetMapping("/{id}")
  @ResponseStatus(OK)
  @Operation(summary = "Get a product by id")
  ResponseEntity<ProductResponseDTO> findById(@PathVariable("id") String id);

  @GetMapping()
  @ResponseStatus(OK)
  @Operation(summary = "Get all products paginated")
  ResponseEntity<Page<ProductResponseDTO>> findAll(@RequestParam(required = false, defaultValue = "10")
                                                   @Min(1)
                                                   int size,
                                                   @RequestParam(required = false, defaultValue = "0")
                                                   @Min(0)
                                                   int page,
                                                   @RequestParam(required = false, defaultValue = "name")
                                                   @NotNull
                                                   String[] sort);

  @GetMapping("all")
  @ResponseStatus(OK)
  @Operation(summary = "Get all products")
  ResponseEntity<List<ProductResponseDTO>> findAll();

  @PostMapping
  @ResponseStatus(CREATED)
  @Operation(summary = "save product")
  ResponseEntity<ProductResponseDTO> save(@RequestBody ProductRequestDTO dto);

  @PutMapping("/{id}")
  @ResponseStatus(OK)
  @Operation(summary = "Get all product")
  ResponseEntity<ProductResponseDTO> update(@RequestBody ProductRequestDTO dto,
                                            @PathVariable String id);

  @PatchMapping("/{id}")
  @ResponseStatus(OK)
  @Operation(summary = "update part of product")
  ResponseEntity<ProductResponseDTO> patchUpdate(@RequestBody ProductRequestDTO dto,
                                                 @PathVariable String id);

  @DeleteMapping("/{id}")
  @ResponseStatus(NO_CONTENT)
  @Operation(summary = "delete a product")
  ResponseEntity<Void> delete(@PathVariable String id);

}