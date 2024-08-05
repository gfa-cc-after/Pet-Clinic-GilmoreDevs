package com.greenfoxacademy.backend.services;

import com.greenfoxacademy.backend.dtos.*;
import com.greenfoxacademy.backend.errors.UserAlreadyExistsError;
import com.greenfoxacademy.backend.models.User;
import org.springframework.stereotype.Service;

/**
 * Service to manage {@link User} related actions.
 */
@Service
public interface UserService {
  RegisterResponseDto register(RegisterRequestDto userDto) throws UserAlreadyExistsError;

  LoginResponseDto login(LoginRequestDto loginRequestDto) throws Exception;

  ProfileUpdateResponseDto profileUpdate(ProfileUpdateRequestDto profileUpdateRequestDto) throws Exception;
}
