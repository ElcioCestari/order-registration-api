package com.brotherselectronics.orderregistration.controllers;

import com.brotherselectronics.orderregistration.controllers.docs.UserControllerSwagger;
import com.brotherselectronics.orderregistration.domains.dtos.SystemUserCreateRequestDTO;
import com.brotherselectronics.orderregistration.domains.dtos.SystemUserResponseDTO;
import com.brotherselectronics.orderregistration.domains.dtos.SystemUserUpdateRequestDTO;
import com.brotherselectronics.orderregistration.services.SystemUserService;
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
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController implements UserControllerSwagger {
  private final SystemUserService systemUserService;

  @GetMapping
  public ResponseEntity<List<SystemUserResponseDTO>> getUsers() {
    return ok(this.systemUserService.findAll());
  }

  @PostMapping("/login")
  public ResponseEntity<SystemUserResponseDTO> getUser() {
    return ok(this.systemUserService.getLoggedUser());
  }

  @GetMapping("/{id}")
  public ResponseEntity<SystemUserResponseDTO> getUserById(@PathVariable String id) {
    return ok(this.systemUserService.findById(id));
  }

  @PostMapping
  public ResponseEntity<SystemUserResponseDTO> createUser(@Valid @RequestBody SystemUserCreateRequestDTO dto) {
    return status(CREATED).body(this.systemUserService.save(dto));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteUser(@PathVariable String id) {
    this.systemUserService.delete(id);
    return status(NO_CONTENT).build();
  }

  @PutMapping("/{id}")
  public ResponseEntity<SystemUserResponseDTO> updateUser(@Valid @RequestBody SystemUserUpdateRequestDTO dto,
                                                          @PathVariable String id) {
    return ok(this.systemUserService.update(dto, id));
  }
}