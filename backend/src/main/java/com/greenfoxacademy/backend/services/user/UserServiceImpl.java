package com.greenfoxacademy.backend.services.user;

import com.greenfoxacademy.backend.dtos.*;
import com.greenfoxacademy.backend.errors.UserAlreadyExistsError;
import com.greenfoxacademy.backend.models.User;
import com.greenfoxacademy.backend.repositories.UserRepository;
import com.greenfoxacademy.backend.services.auth.AuthService;
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

  @Override
  public RegisterResponseDto register(
      RegisterRequestDto registerRequestDto) throws UserAlreadyExistsError {

    User user = User.builder()
            .email(registerRequestDto.email())
            .firstName(registerRequestDto.firstName())
            .lastName(registerRequestDto.lastName())
            .password(passwordEncoder.encode(registerRequestDto.password()))
            .build();
    try {
      return new RegisterResponseDto(userRepository.save(user).getId());
    } catch (Exception e) {
      throw new UserAlreadyExistsError("Email is already taken!");
    }
  }


  @Override
  public LoginResponseDto login(
      LoginRequestDto loginRequestDto) throws Exception {
    User user = userRepository
        .findByEmail(loginRequestDto.email()).orElseThrow(() -> new Exception("User not found"));
    if (!passwordEncoder.matches(loginRequestDto.password(), user.getPassword())) {
      throw new Exception("Invalid password");
    }
    return new LoginResponseDto(authService.generateToken(user));
  }

  @Override
  public ProfileUpdateResponseDto profileUpdate(
      String email, ProfileUpdateRequestDto profileUpdateRequestDto) throws Exception {
    User user = userRepository
        .findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    user.setEmail(profileUpdateRequestDto.email());
    user.setFirstName(profileUpdateRequestDto.firstName());
    user.setLastName(profileUpdateRequestDto.lastName());
    user.setPassword(passwordEncoder.encode(profileUpdateRequestDto.password()));

    User updatedUser = userRepository.save(user);
    return new ProfileUpdateResponseDto(updatedUser.getId());
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return userRepository
        .findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("No such user!"));
  }
}