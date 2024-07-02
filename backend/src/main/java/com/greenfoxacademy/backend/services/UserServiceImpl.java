package com.greenfoxacademy.backend.services;

import com.greenfoxacademy.backend.dtos.RegisterUserDto;
import com.greenfoxacademy.backend.models.User;
import com.greenfoxacademy.backend.repositories.UserRepository;
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
  private ModelMapper modelMapper;

  @Override
  public void register(RegisterUserDto userDto) {
    User user = this.mapToEntity(userDto);
    user.setPassword(passwordEncoder.encode(userDto.getPassword()));
    userRepository.save(user);
  }

  private RegisterUserDto mapToDto(User user) {
    RegisterUserDto registerUserDto = modelMapper.map(user, RegisterUserDto.class);
    return registerUserDto;
  }

  private User mapToEntity(RegisterUserDto userDto) {
    User user = modelMapper.map(userDto, User.class);
    return user;
  }
}


