package com.greenfoxacademy.backend.services.user;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.greenfoxacademy.backend.config.FeatureFlags;
import com.greenfoxacademy.backend.dtos.LoginRequestDto;
import com.greenfoxacademy.backend.dtos.ProfileUpdateRequestDto;
import com.greenfoxacademy.backend.dtos.RegisterRequestDto;
import com.greenfoxacademy.backend.errors.CannotUpdateUserException;
import com.greenfoxacademy.backend.errors.UserAlreadyExistsError;
import com.greenfoxacademy.backend.models.Owner;
import com.greenfoxacademy.backend.repositories.OwnerRepository;
import com.greenfoxacademy.backend.services.auth.AuthService;
import com.greenfoxacademy.backend.services.mail.EmailService;

import java.util.Optional;
import java.util.UUID;
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
  private OwnerService ownerService;

  private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

  @Mock
  private OwnerRepository ownerRepository;

  @Mock
  private AuthService authService;
  @Mock
  private EmailService emailService;
  @Mock
  private FeatureFlags featureFlags;


  @BeforeEach
  void setUp() {
    Mockito.reset(ownerRepository);
    ownerService = new OwnerServiceImpl(
            ownerRepository,
            passwordEncoder,
            authService,
            emailService,
            featureFlags);
  }

  @DisplayName("Register a new user if email not taken")
  @Test
  void register() {
    // Given
    Owner asSaved = Owner.builder().id(1L).build();
    RegisterRequestDto registerRequestDto = new RegisterRequestDto(
            "fistName",
            "lastName",
            "some@email.com",
            "SomePassword123");

    // When
    when(ownerRepository.save(any())).thenReturn(asSaved);
    ownerService.register(registerRequestDto);

    // Then
    Mockito.verify(ownerRepository, Mockito.times(1)).save(any());
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
    when(ownerRepository.save(any()))
            .thenThrow(new UserAlreadyExistsError("Email is already taken!"));

    Assertions.assertThrows(
            UserAlreadyExistsError.class,
            () -> ownerService.register(registerRequestDto)
    );

  }

  @DisplayName("Login a user successfully")
  @Test
  void login() throws Exception {
    // Given
    Owner owner = Owner.builder()
            .id(1L)
            .email("email")
            .password(passwordEncoder.encode("password"))
            .build();
    LoginRequestDto userLoginRequestDto = new LoginRequestDto("email", "password");

    // When
    when(ownerRepository.findByEmail(anyString())).thenReturn(Optional.of(owner));
    when(authService.generateToken(any())).thenReturn("token");

    ownerService.login(userLoginRequestDto);

    // Then
    Mockito.verify(
            ownerRepository,
            Mockito.times(1)).findByEmail(anyString()
    );
  }

  @DisplayName("Login a user unsuccessfully")
  @Test
  void loginUnsuccessful() throws Exception {
    // Given
    Owner owner = Owner.builder()
            .id(1L)
            .email("email")
            .password(passwordEncoder.encode("passwordNOOP"))
            .build();
    LoginRequestDto userLoginRequestDto = new LoginRequestDto(
            "email",
            "password"
    );

    // When
    when(ownerRepository.findByEmail(anyString())).thenReturn(Optional.of(owner));

    Assertions.assertThrows(Exception.class, () -> ownerService.login(userLoginRequestDto));

    // Then
    Mockito.verify(
            ownerRepository,
            Mockito.times(1)).findByEmail(anyString()
    );
  }

  @DisplayName("Update a user profile successfully")
  @Test
  void profileUpdate() throws Exception {
    // Given
    Owner owner = Owner.builder()
            .id(1L)
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
    when(ownerRepository.findByEmail(anyString())).thenReturn(Optional.of(owner));
    when(ownerRepository.save(any())).thenReturn(owner);

    ownerService.profileUpdate(email, profileUpdateRequestDto);

    // Then
    Mockito
            .verify(ownerRepository, Mockito.times(1))
            .findByEmail(anyString());
  }

  @DisplayName("Update a user profile unsuccessfully")
  @Test
  void profileUpdateUnsuccessful() throws Exception {
    // Given
    Owner owner = Owner.builder().id(1L).email("email")
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
    when(ownerRepository.findByEmail(anyString())).thenReturn(Optional.of(owner));
    when(ownerRepository.existsByEmail("new@email.com")).thenReturn(true);

    // Then
    Assertions.assertThrows(
            CannotUpdateUserException.class,
            () -> ownerService.profileUpdate(email, profileUpdateRequestDto)
    );
  }

  @Test
  void loadUserByUsername() {
    // Given
    Owner owner = Owner.builder()
            .id(1L)
            .email("email")
            .password(passwordEncoder.encode("password"))
            .build();
    String email = "email";
    // When
    when(ownerRepository.findByEmail(anyString())).thenReturn(Optional.of(owner));
    ownerService.loadUserByUsername(email);
    // Then
    Mockito.verify(
            ownerRepository,
            Mockito.times(1)
    ).findByEmail(anyString());
  }

  @Test
  void verifyUserById() {
    when(featureFlags.isEmailVerificationEnabled()).thenReturn(true);
    UUID id = UUID.randomUUID();
    Owner owner = Owner.builder()
            .id(1L)
            .email("email")
            .password(passwordEncoder.encode("password"))
            .verificationId(id)
            .build();
    // When
    when(ownerRepository.findByVerificationId(id)).thenReturn(Optional.of(owner));
    ownerService.verifyUser(id);
    // Then
    Mockito.verify(
            ownerRepository,
            Mockito.times(1)
    ).save(any(Owner.class));
  }

  @Test
  void throwsExceptionEmailIsNotVerified() {
    UUID id = UUID.randomUUID();
    Owner owner = Owner.builder()
            .id(1L)
            .email("email")
            .password(passwordEncoder.encode("password"))
            .verificationId(id)
            .build();

    LoginRequestDto loginRequestDto = new LoginRequestDto(owner.getEmail(), "password");

    when(ownerRepository.findByEmail(owner.getEmail())).thenReturn(Optional.of(owner));
    Assertions.assertThrows(Exception.class, () -> ownerService.login(loginRequestDto));

  }
}
