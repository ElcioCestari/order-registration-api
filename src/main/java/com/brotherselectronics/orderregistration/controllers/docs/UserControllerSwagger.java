package com.brotherselectronics.orderregistration.controllers.docs;

import com.brotherselectronics.orderregistration.domains.dtos.SystemUserCreateRequestDTO;
import com.brotherselectronics.orderregistration.domains.dtos.SystemUserResponseDTO;
import com.brotherselectronics.orderregistration.domains.dtos.SystemUserUpdateRequestDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/users")
@Tag(name = "User")
public interface UserControllerSwagger {

  @Operation(summary = "Get all users")
  @ResponseStatus(OK)
  @GetMapping
  ResponseEntity<List<SystemUserResponseDTO>> getUsers();


  @Operation(summary = "Get all users")
  @ResponseStatus(OK)
  @PostMapping("/login")
  ResponseEntity<SystemUserResponseDTO> getUser();


  @Operation(summary = "Get user by id")
  @ResponseStatus(OK)
  @GetMapping("/{id}")
  ResponseEntity<SystemUserResponseDTO> getUserById(@PathVariable String id);


  @Operation(summary = "Create user")
  @ResponseStatus(CREATED)
  @PostMapping
  ResponseEntity<SystemUserResponseDTO> createUser(@RequestBody SystemUserCreateRequestDTO dto);


  @Operation(summary = "Delete user")
  @ResponseStatus(NO_CONTENT)
  @DeleteMapping("/{id}")
  ResponseEntity<Void> deleteUser(@PathVariable String id);


  @Operation(summary = "Update user")
  @ResponseStatus(OK)
  @PutMapping("/{id}")
  ResponseEntity<SystemUserResponseDTO> updateUser(@RequestBody SystemUserUpdateRequestDTO dto,
                                                   @PathVariable String id);
}