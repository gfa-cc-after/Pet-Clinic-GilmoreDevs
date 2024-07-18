package com.greenfoxacademy.backend.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.greenfoxacademy.backend.dtos.RegisterUserDto;
import com.greenfoxacademy.backend.errors.UserAlreadyExistsError;
import com.greenfoxacademy.backend.repositories.UserRepository;
import com.greenfoxacademy.backend.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * This class runs a test to verify if the register method in the userService is properly called.
 */
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

  @Mock
  private UserService userService;
  @Mock
  private UserRepository userRepository;

  @InjectMocks
  private UserController userController;

  @Test
  public void registerMethodIsSuccessfullyCalled() throws Exception, UserAlreadyExistsError {
    RegisterUserDto registerUserDto = new RegisterUserDto();
    registerUserDto.setFirstName("John");
    registerUserDto.setLastName("Doe");
    registerUserDto.setEmail("john.doe@gmail.com");
    registerUserDto.setPassword("password");

    userService.register(registerUserDto);

    verify(userService, times(1)).register(registerUserDto);

  }

}
