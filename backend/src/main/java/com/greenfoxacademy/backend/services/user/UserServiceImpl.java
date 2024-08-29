package com.greenfoxacademy.backend.services.user;

import com.greenfoxacademy.backend.dtos.LoginRequestDto;
import com.greenfoxacademy.backend.dtos.LoginResponseDto;
import com.greenfoxacademy.backend.dtos.ProfileUpdateRequestDto;
import com.greenfoxacademy.backend.dtos.ProfileUpdateResponseDto;
import com.greenfoxacademy.backend.dtos.RegisterRequestDto;
import com.greenfoxacademy.backend.dtos.RegisterResponseDto;
import com.greenfoxacademy.backend.errors.CannotSendEmailError;
import com.greenfoxacademy.backend.errors.CannotUpdateUserException;
import com.greenfoxacademy.backend.errors.UserAlreadyExistsError;
import com.greenfoxacademy.backend.models.User;
import com.greenfoxacademy.backend.repositories.UserRepository;
import com.greenfoxacademy.backend.services.auth.AuthService;
import com.greenfoxacademy.backend.services.mail.EmailService;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;

import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service implementation to manage {@link UserService}.
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final AuthService authService;
  private final EmailService emailService;

  @Override
  public RegisterResponseDto register(RegisterRequestDto registerRequestDto)
          throws UserAlreadyExistsError, CannotSendEmailError {

    // @formatter:off
    User user = User.builder()
            .email(registerRequestDto.email())
            .firstName(registerRequestDto.firstName())
            .lastName(registerRequestDto.lastName())
            .password(passwordEncoder.encode(registerRequestDto.password()))
            .verificationId(UUID.randomUUID())
            .build();
    // @formatter:on
    try {
      User saved = userRepository.save(user);
      emailService.sendRegistrationEmail(
              saved.getEmail(),
              saved.getFirstName(),
              saved.getVerificationId()
      );
      return new RegisterResponseDto(saved.getId());
    } catch (MessagingException e) {
      throw new CannotSendEmailError(
              "Sorry we can not send a verification email please contact the Pentagon."
      );
    } catch (Exception e) {
      throw new UserAlreadyExistsError("Email is already taken!");
    }
  }


  @Override
  public LoginResponseDto login(LoginRequestDto loginRequestDto) throws Exception {
    User user = userRepository.findByEmail(loginRequestDto.email())
            .orElseThrow(() -> new Exception("User not found"));
    if (!user.isEnabled()) {
      throw new Exception("User's email is not verified");
    } else if (!passwordEncoder.matches(loginRequestDto.password(), user.getPassword())) {
      throw new Exception("Invalid password");
    }
    return new LoginResponseDto(authService.generateToken(user));
  }

  @Override
  public ProfileUpdateResponseDto profileUpdate(
          String email,
          ProfileUpdateRequestDto profileUpdateRequestDto
  ) throws CannotUpdateUserException {
    User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    if (
            userRepository.existsByEmail(profileUpdateRequestDto.email())
                    && !email.equals(profileUpdateRequestDto.email())
    ) {
      throw new CannotUpdateUserException("Email is already taken!");
    }
    user.setEmail(profileUpdateRequestDto.email());
    user.setFirstName(profileUpdateRequestDto.firstName());
    user.setLastName(profileUpdateRequestDto.lastName());
    user.setPassword(passwordEncoder.encode(profileUpdateRequestDto.password()));

    User updatedUser = userRepository.save(user);
    return new ProfileUpdateResponseDto(authService.generateToken(updatedUser));
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return userRepository.findByEmail(username)
            .orElseThrow(() -> new UsernameNotFoundException("No such user!"));
  }

  /**
   * Delete the user by username.
   */
  @Transactional
  @Override
  public void deleteProfile(String username) {
    userRepository.deleteByEmail(username);
  }

  /**
   * Verify the user by id sent as email.
   */
  public void verifyUser(UUID id) {
    User userWithId = userRepository.findByVerificationId(id).orElseThrow();
    userWithId.setVerificationId(null);
    userRepository.save(userWithId);
  }
}