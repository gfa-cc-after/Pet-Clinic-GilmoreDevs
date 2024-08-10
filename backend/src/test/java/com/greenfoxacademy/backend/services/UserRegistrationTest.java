package com.greenfoxacademy.backend.services;

import com.greenfoxacademy.backend.dtos.RegisterRequestDto;
import com.greenfoxacademy.backend.dtos.RegisterResponseDto;
import com.greenfoxacademy.backend.errors.UserAlreadyExistsError;
import com.greenfoxacademy.backend.repositories.UserRepository;
import com.greenfoxacademy.backend.services.user.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * This class runs a test that verifies if a user is successfully registered in the database.
 */
@SpringBootTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class UserRegistrationTest {

  @Autowired
  private UserService userService;

  @Autowired
  private UserRepository userRepository;

  @Test
  public void userIsSuccessfulRegisteredInDatabase() throws Exception, UserAlreadyExistsError {
    RegisterRequestDto newUser = new RegisterRequestDto(
        "John",
        "Doe",
        "john.doe@example.com",
        "password"
    );

    RegisterResponseDto registeredUserDto = userService.register(newUser);

    Assertions.assertEquals(1, registeredUserDto.id());

    boolean isRegistered = userRepository.existsByEmail("john.doe@example.com");
    Assertions.assertTrue(isRegistered);
  }
}
