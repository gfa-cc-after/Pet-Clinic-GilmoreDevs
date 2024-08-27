package com.greenfoxacademy.backend.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.greenfoxacademy.backend.dtos.EmailSentDTO;
import com.greenfoxacademy.backend.dtos.RegisterRequestDto;
import com.greenfoxacademy.backend.dtos.RegisterResponseDto;
import com.greenfoxacademy.backend.errors.UserAlreadyExistsError;
import com.greenfoxacademy.backend.repositories.UserRepository;
import com.greenfoxacademy.backend.services.mail.EmailService;
import com.greenfoxacademy.backend.services.user.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import java.util.UUID;

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

  @MockBean
  private EmailService emailService;

  @Test
  public void userIsSuccessfulRegisteredInDatabase() throws Exception, UserAlreadyExistsError {

    when(emailService.sendRegistrationEmail(anyString(), anyString(), any(UUID.class)))
            .thenReturn(new EmailSentDTO());
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
