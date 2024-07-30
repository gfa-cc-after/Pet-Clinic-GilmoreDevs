package com.greenfoxacademy.backend.services;

import com.greenfoxacademy.backend.dtos.LoginRequestDto;
import com.greenfoxacademy.backend.dtos.LoginResponseDto;
import com.greenfoxacademy.backend.dtos.RegisterRequestDto;
import com.greenfoxacademy.backend.dtos.RegisterResponseDto;
import com.greenfoxacademy.backend.errors.UserAlreadyExistsError;
import com.greenfoxacademy.backend.models.User;
import com.greenfoxacademy.backend.repositories.UserRepository;
import com.greenfoxacademy.backend.services.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
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
    User user = userRepository.findByEmail(loginRequestDto.email())
        .orElseThrow(() -> new Exception("User not found"));
    if (!passwordEncoder.matches(loginRequestDto.password(), user.getPassword())) {
      throw new Exception("Invalid password");
    }
    return new LoginResponseDto(jwtUtil.createToken(null, user.getEmail()));
  }
}