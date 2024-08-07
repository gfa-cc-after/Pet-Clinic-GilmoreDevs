package com.greenfoxacademy.backend.services;

import com.greenfoxacademy.backend.dtos.*;
import com.greenfoxacademy.backend.errors.CannotUpdateUserException;
import com.greenfoxacademy.backend.errors.UserAlreadyExistsError;
import com.greenfoxacademy.backend.models.User;
import com.greenfoxacademy.backend.repositories.UserRepository;
import com.greenfoxacademy.backend.services.jwt.JwtUtil;

import java.util.HashMap;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
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
  private final PasswordEncoder passwordEncoder;
  private final UserRepository userRepository;
  private final ModelMapper modelMapper;
  private final JwtUtil jwtUtil;

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
    HashMap<String, Object> claims = new HashMap<>();
    claims.put("firstName", user.getFirstName());
    claims.put("lastName", user.getLastName());
    return new LoginResponseDto(jwtUtil.createToken(claims, user.getEmail()));
  }

  @Override
  public ProfileUpdateResponseDto profileUpdate(User user, ProfileUpdateRequestDto profileUpdateRequestDto) throws CannotUpdateUserException {

    User userInDatabase = userRepository.findByEmail(user.getEmail()).orElseThrow(() -> new UsernameNotFoundException("No such user!"));
    if (userRepository.findByEmail(profileUpdateRequestDto.email()).isPresent() && !profileUpdateRequestDto.email().equals(user.getEmail())) {
      throw new CannotUpdateUserException("Email is already taken!");
    }
    userInDatabase.setEmail(profileUpdateRequestDto.email());
    userInDatabase.setFirstName(profileUpdateRequestDto.firstName());
    userInDatabase.setLastName(profileUpdateRequestDto.lastName());
    userInDatabase.setPassword(passwordEncoder.encode(profileUpdateRequestDto.password()));
    User updatedUser = userRepository.save(userInDatabase);
    return new ProfileUpdateResponseDto(updatedUser.getId());
  }

  private ProfileUpdateResponseDto mapToProfileUpdateResponseDto(User user) {
    return new ProfileUpdateResponseDto(user.getId());
  }

  @Override
  public User loadUserByUsername(String username) throws UsernameNotFoundException {
    User asd = userRepository.findByEmail(username).orElse(null);
    System.out.println(asd);
    return userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("No such user!"));
  }
}