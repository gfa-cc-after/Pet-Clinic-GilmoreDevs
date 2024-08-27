package com.greenfoxacademy.backend.services.user;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.greenfoxacademy.backend.dtos.LoginRequestDto;
import com.greenfoxacademy.backend.dtos.ProfileUpdateRequestDto;
import com.greenfoxacademy.backend.dtos.RegisterRequestDto;
import com.greenfoxacademy.backend.errors.CannotUpdateUserException;
import com.greenfoxacademy.backend.errors.UserAlreadyExistsError;
import com.greenfoxacademy.backend.models.User;
import com.greenfoxacademy.backend.repositories.UserRepository;
import com.greenfoxacademy.backend.services.auth.AuthService;

import java.util.Optional;
import java.util.UUID;

import com.greenfoxacademy.backend.services.mail.EmailService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
  private UserServiceImpl userService;

  private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

  @Mock
  private UserRepository userRepository;

  @Mock
  private AuthService authService;
  @Mock
  private EmailService emailService;


  @BeforeEach
  void setUp() {
    Mockito.reset(userRepository);
    userService = new UserServiceImpl(userRepository, passwordEncoder, authService, emailService);
  }

  @DisplayName("Register a new user if email not taken")
  @Test
  void register() {
    // Given
    User asSaved = User.builder().id(1).build();
    RegisterRequestDto registerRequestDto = new RegisterRequestDto(
            "fistName",
            "lastName",
            "some@email.com",
            "SomePassword123");

    // When
    when(userRepository.save(any())).thenReturn(asSaved);
    userService.register(registerRequestDto);

    // Then
    Mockito.verify(userRepository, Mockito.times(1)).save(any());
  }

  @DisplayName("Does not register a new user if email is taken")
  @Test
  void registerFails() {
    // Given
    RegisterRequestDto registerRequestDto = new RegisterRequestDto(
            "fistName",
            "lastName",
            "some@email.com",
            "SomePassword123");

    // When
    when(userRepository.save(any()))
            .thenThrow(new UserAlreadyExistsError("Email is already taken!"));

    Assertions.assertThrows(
            UserAlreadyExistsError.class,
            () -> userService.register(registerRequestDto)
    );

  }

  @DisplayName("Login a user successfully")
  @Test
  void login() throws Exception {
    // Given
    User user = User.builder()
            .id(1)
            .email("email")
            .password(passwordEncoder.encode("password"))
            .build();
    LoginRequestDto userLoginRequestDto = new LoginRequestDto("email", "password");

    // When
    when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
    when(authService.generateToken(any())).thenReturn("token");

    userService.login(userLoginRequestDto);

    // Then
    Mockito.verify(
            userRepository,
            Mockito.times(1)).findByEmail(anyString()
    );
  }

  @DisplayName("Login a user unsuccessfully")
  @Test
  void loginUnsuccessful() throws Exception {
    // Given
    User user = User.builder()
            .id(1)
            .email("email")
            .password(passwordEncoder.encode("passwordNOOP"))
            .build();
    LoginRequestDto userLoginRequestDto = new LoginRequestDto(
            "email",
            "password"
    );

    // When
    when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));

    Assertions.assertThrows(Exception.class, () -> userService.login(userLoginRequestDto));

    // Then
    Mockito.verify(
            userRepository,
            Mockito.times(1)).findByEmail(anyString()
    );
  }

  @DisplayName("Update a user profile successfully")
  @Test
  void profileUpdate() throws Exception {
    // Given
    User user = User.builder()
            .id(1)
            .email("email")
            .password(passwordEncoder.encode("password"))
            .build();
    String email = "email";
    ProfileUpdateRequestDto profileUpdateRequestDto = new ProfileUpdateRequestDto(
            "newEmail",
            "newFirstName",
            "newLastName",
            "newPassword");

    // When
    when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
    when(userRepository.save(any())).thenReturn(user);

    userService.profileUpdate(email, profileUpdateRequestDto);

    // Then
    Mockito
            .verify(userRepository, Mockito.times(1))
            .findByEmail(anyString());
  }

  @DisplayName("Update a user profile unsuccessfully")
  @Test
  void profileUpdateUnsuccessful() throws Exception {
    // Given
    User user = User.builder().id(1).email("email")
            .password(passwordEncoder.encode("password"))
            .build();
    String email = "email";
    // @formatter:off

    ProfileUpdateRequestDto profileUpdateRequestDto = new ProfileUpdateRequestDto(
            "newEmail",
            "newFirstName",
            "new@email.com",
            "newPassword");
    // @formatter:on

    // When
    when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
    when(userRepository.existsByEmail("new@email.com")).thenReturn(true);

    // Then
    Assertions.assertThrows(
            CannotUpdateUserException.class,
            () -> userService.profileUpdate(email, profileUpdateRequestDto)
    );
  }

  @Test
  void loadUserByUsername() {
    // Given
    User user = User.builder()
            .id(1)
            .email("email")
            .password(passwordEncoder.encode("password"))
            .build();
    String email = "email";
    // When
    when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
    userService.loadUserByUsername(email);
    // Then
    Mockito.verify(
            userRepository,
            Mockito.times(1)
    ).findByEmail(anyString());
  }

  @Test
  void verifyUserByID() {
    UUID id = UUID.randomUUID();
    User user = User.builder()
            .id(1)
            .email("email")
            .password(passwordEncoder.encode("password"))
            .verificationId(id)
            .build();
    // When
    when(userRepository.findByVerificationID(id)).thenReturn(Optional.of(user));
    userService.verifyUser(id);
    // Then
    Mockito.verify(
            userRepository,
            Mockito.times(1)
    ).save(any(User.class));
  }

  @Test
  void throwsExceptionEmailIsNotVerified() {
    UUID id = UUID.randomUUID();
    User user = User.builder()
            .id(1)
            .email("email")
            .password(passwordEncoder.encode("password"))
            .verificationId(id)
            .build();

    LoginRequestDto loginRequestDto = new LoginRequestDto(user.getEmail(), "password");

    when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
    Assertions.assertThrows(Exception.class, () -> userService.login(loginRequestDto));

  }
}
