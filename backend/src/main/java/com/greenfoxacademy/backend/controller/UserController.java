package com.greenfoxacademy.backend.controller;

import com.greenfoxacademy.backend.dtos.RegisterUserDto;
import com.greenfoxacademy.backend.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller where endpoints are handled.
 */
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:8080")
public class UserController {
  private final UserService userService;

  @CrossOrigin(origins = "http://localhost:5173")
  @PostMapping("/register")
  public ResponseEntity<?> registerUser(@RequestBody RegisterUserDto registerUserDto) {
    if (!userService.emailValidation(registerUserDto.getEmail())) {
      return ResponseEntity.badRequest().body("Email is not valid!");
    } else if (userService.existsByEmail(registerUserDto.getEmail())) {
      return ResponseEntity.badRequest().body("Email is already exist!");
    } else {
      userService.register(registerUserDto);
      return ResponseEntity.ok().build();
    }
  }
}
