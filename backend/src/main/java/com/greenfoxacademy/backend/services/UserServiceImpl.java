package com.greenfoxacademy.backend.services;

import com.greenfoxacademy.backend.dtos.RegisterUserDTO;
import com.greenfoxacademy.backend.models.User;
import com.greenfoxacademy.backend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

/**
 * Service implementation to manage {@link UserService}.
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;
  private ModelMapper modelMapper;
  
  @Override
  public void register(User newUser) {
    userRepository.save(newUser);
  }
  
  private RegisterUserDTO mapToDTO(User user) {
    RegisterUserDTO registerUserDTO = modelMapper.map(user, RegisterUserDTO.class);
    return registerUserDTO;
  }
  
  private User mapToEntity(RegisterUserDTO userDto) {
    User user = modelMapper.map(userDto, User.class);
    return user;
  }
}


