package com.greenfoxacademy.backend.services.user;

import com.greenfoxacademy.backend.dtos.LoginRequestDto;
import com.greenfoxacademy.backend.dtos.LoginResponseDto;
import com.greenfoxacademy.backend.dtos.ProfileUpdateRequestDto;
import com.greenfoxacademy.backend.dtos.ProfileUpdateResponseDto;
import com.greenfoxacademy.backend.dtos.RegisterRequestDto;
import com.greenfoxacademy.backend.dtos.RegisterResponseDto;
import com.greenfoxacademy.backend.errors.CannotUpdateUserException;
import com.greenfoxacademy.backend.errors.UserAlreadyExistsError;
import com.greenfoxacademy.backend.models.User;
import com.greenfoxacademy.backend.repositories.UserRepository;
import com.greenfoxacademy.backend.services.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * Service implementation to manage {@link UserService}.
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final AuthService authService;

  @Override
  public RegisterResponseDto register(RegisterRequestDto registerRequestDto)
          throws UserAlreadyExistsError {

    // @formatter:off
    User user = User.builder()
            .email(registerRequestDto.email())
            .firstName(registerRequestDto.firstName())
            .lastName(registerRequestDto.lastName())
            .password(passwordEncoder.encode(registerRequestDto.password()))
            .build();
    // @formatter:on
    try {
      return new RegisterResponseDto(userRepository.save(user).getId());
    } catch (Exception e) {
      throw new UserAlreadyExistsError("Email is already taken!");
    }
  }


  @Override
  public LoginResponseDto login(LoginRequestDto loginRequestDto) throws Exception {
    User user = userRepository.findByEmail(loginRequestDto.email())
            .orElseThrow(() -> new Exception("User not found"));
    if (!passwordEncoder.matches(loginRequestDto.password(), user.getPassword())) {
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
            && !Objects.equals(email, profileUpdateRequestDto.email())
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
}