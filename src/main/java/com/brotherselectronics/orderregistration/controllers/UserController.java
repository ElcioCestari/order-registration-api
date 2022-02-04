package com.brotherselectronics.orderregistration.controllers;

import com.brotherselectronics.orderregistration.domains.dtos.SystemUserRequestDTO;
import com.brotherselectronics.orderregistration.services.SystemUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final SystemUserService systemUserService;

    @Autowired
    public UserController(SystemUserService systemUserService) {
        this.systemUserService = systemUserService;
    }

    @GetMapping
    public ResponseEntity getUsers() {
        return ResponseEntity.ok(this.systemUserService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity getUserById(@PathVariable String id) {
        return ResponseEntity.ok(this.systemUserService.findById(id));
    }

    @PostMapping
    public ResponseEntity createUser(@RequestBody SystemUserRequestDTO dto) {
        return ResponseEntity.ok(this.systemUserService.save(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteUser(@PathVariable String id) {
        this.systemUserService.delete(id);
        return ResponseEntity.ok(200);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateUsers(SystemUserRequestDTO dto, String id) {
        return ResponseEntity.ok(this.systemUserService.update(dto, id));
    }
}