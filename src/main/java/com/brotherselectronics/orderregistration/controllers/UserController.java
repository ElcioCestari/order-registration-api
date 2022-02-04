package com.brotherselectronics.orderregistration.controllers;

import com.brotherselectronics.orderregistration.services.SystemUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}