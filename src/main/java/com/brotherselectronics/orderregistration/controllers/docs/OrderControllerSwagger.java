package com.brotherselectronics.orderregistration.controllers.docs;


import com.brotherselectronics.orderregistration.domains.dtos.OrderRequestDTO;
import com.brotherselectronics.orderregistration.domains.dtos.OrderResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/orders")
@Tag(name = "Order")
public interface OrderControllerSwagger {

  @GetMapping("/{id}")
  @Operation(summary = "Get order by id")
  @ResponseStatus(OK)
  ResponseEntity<OrderResponseDTO> findById(@PathVariable("id") String id);

  @Operation(summary = "Get all orders")
  @ResponseStatus(OK)
  ResponseEntity<List<OrderResponseDTO>> findAll();

  @PostMapping
  @Operation(summary = "save order")
  @ResponseStatus(OK)
  ResponseEntity<OrderResponseDTO> save(@RequestBody OrderRequestDTO dto);

  @PutMapping("/{id}")
  @Operation(summary = "update order")
  @ResponseStatus(OK)
  ResponseEntity<OrderResponseDTO> update(@RequestBody OrderRequestDTO dto, @PathVariable String id);

  @DeleteMapping("/{id}")
  @Operation(summary = "delete order")
  @ResponseStatus(OK)
  ResponseEntity<Void> delete(@PathVariable String id);

}