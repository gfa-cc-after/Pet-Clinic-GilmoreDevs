package com.greenfoxacademy.backend.services.user;

import com.greenfoxacademy.backend.config.SecurityConfig;
import com.greenfoxacademy.backend.dtos.*;
import com.greenfoxacademy.backend.errors.UserAlreadyExistsError;
import com.greenfoxacademy.backend.models.User;
import com.greenfoxacademy.backend.repositories.UserRepository;


import com.greenfoxacademy.backend.services.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
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
  private final ModelMapper modelMapper;
  private final PasswordEncoder passwordEncoder;
  private final AuthService authService;

  @Override
  public RegisterResponseDto register(RegisterRequestDto userDto) throws UserAlreadyExistsError {
    User user = mapToEntity(userDto);
    user.setPassword(passwordEncoder.encode(userDto.getPassword()));
    try {
      return mapToRegisterResponseDto(userRepository.save(user));
    } catch (Exception e) {
      throw new UserAlreadyExistsError("Email is already taken!");
    }
  }

  private RegisterResponseDto mapToRegisterResponseDto(User user) {
    return new RegisterResponseDto(user.getId());
  }

  private User mapToEntity(RegisterRequestDto userDto) {
    return modelMapper.map(userDto, User.class);
  }

  @Override
  public LoginResponseDto login(LoginRequestDto loginRequestDto) throws Exception {
    User user = userRepository.findByEmail(loginRequestDto.email()).orElseThrow(() -> new Exception("User not found"));
    if (!passwordEncoder.matches(loginRequestDto.password(), user.getPassword())) {
      throw new Exception("Invalid password");
    }
    return new LoginResponseDto(authService.generateToken(user));
  }

  @Override
  public ProfileUpdateResponseDto profileUpdate(String email, ProfileUpdateRequestDto profileUpdateRequestDto) throws Exception {
    User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    user.setEmail(profileUpdateRequestDto.email());
    user.setFirstName(profileUpdateRequestDto.firstName());
    user.setLastName(profileUpdateRequestDto.lastName());
    user.setPassword(passwordEncoder.encode(profileUpdateRequestDto.password()));

    User updatedUser = userRepository.save(user);
    return new ProfileUpdateResponseDto(updatedUser.getId());
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("No such user!"));
  }
}