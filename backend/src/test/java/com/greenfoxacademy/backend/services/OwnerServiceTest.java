package com.greenfoxacademy.backend.services;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.greenfoxacademy.backend.controller.UserController;
import com.greenfoxacademy.backend.dtos.RegisterRequestDto;
import com.greenfoxacademy.backend.errors.UserAlreadyExistsError;
import com.greenfoxacademy.backend.repositories.OwnerRepository;
import com.greenfoxacademy.backend.services.user.OwnerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * This class runs a test to verify if the register method in the userService is properly called.
 */
@ExtendWith(MockitoExtension.class)
public class OwnerServiceTest {

  @Mock
  private OwnerService ownerService;
  @Mock
  private OwnerRepository ownerRepository;

  @InjectMocks
  private UserController userController;

  @Test
  public void registerMethodIsSuccessfullyCalled() throws Exception, UserAlreadyExistsError {
    RegisterRequestDto registerRequestDto = new RegisterRequestDto(
            "John",
            "Doe",
            "john.doe@gmail.com",
            "password"
    );

    ownerService.register(registerRequestDto);

    verify(ownerService, times(1)).register(registerRequestDto);

  }

}
