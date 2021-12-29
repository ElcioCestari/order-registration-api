package com.brotherselectronics.orderregistration.controllers;


import com.brotherselectronics.orderregistration.domains.dtos.OrderRequestDTO;
import com.brotherselectronics.orderregistration.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService service;

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") String id){
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody OrderRequestDTO dto) {
        return ResponseEntity.ok(service.save(dto));
    }

}