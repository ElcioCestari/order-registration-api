package com.brotherselectronics.orderregistration.controllers;


import com.brotherselectronics.orderregistration.controllers.docs.OrderControllerSwagger;
import com.brotherselectronics.orderregistration.domains.dtos.OrderRequestDTO;
import com.brotherselectronics.orderregistration.domains.dtos.OrderResponseDTO;
import com.brotherselectronics.orderregistration.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController implements OrderControllerSwagger {

  private final OrderService service;

  @GetMapping("/{id}")
  public ResponseEntity<OrderResponseDTO> findById(@PathVariable("id") String id) {
    return ok(service.findById(id));
  }

  @GetMapping
  public ResponseEntity<List<OrderResponseDTO>> findAll() {
    return ok(service.findAll());
  }

  @PostMapping
  public ResponseEntity<OrderResponseDTO> save(@Valid @RequestBody OrderRequestDTO dto) {
    return status(CREATED).body(service.save(dto));
  }

  @PutMapping("/{id}")
  public ResponseEntity<OrderResponseDTO> update(@Valid @RequestBody OrderRequestDTO dto, @PathVariable String id) {
    return ok(service.update(dto, id));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable String id) {
    service.delete(id);
    return status(NO_CONTENT).build();
  }

}