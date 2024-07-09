package com.greenfoxacademy.backend.controller;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.greenfoxacademy.backend.dtos.RegisterUserDto;
import com.greenfoxacademy.backend.repositories.UserRepository;
import com.greenfoxacademy.backend.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * This class runs a test that verifies if a user is successfully registered in the database.
 */
@SpringBootTest
@AutoConfigureTestDatabase
public class UserRegistrationTest {

  @Autowired
  private UserService userService;

  @Autowired
  private UserRepository userRepository;

  @Test
  public void userIsSuccessfulRegisteredInDatabase() throws Exception {
    RegisterUserDto newUser = new RegisterUserDto();
    newUser.setFirstName("John");
    newUser.setLastName("Doe");
    newUser.setEmail("john.doe@example.com");
    newUser.setPassword("password");

    userService.register(newUser);

    boolean isRegistered = userRepository.existsByEmail("john.doe@example.com");
    assertTrue(isRegistered);
  }
}
