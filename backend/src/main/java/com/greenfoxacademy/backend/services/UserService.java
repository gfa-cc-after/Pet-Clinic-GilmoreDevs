package com.greenfoxacademy.backend.services;

import com.greenfoxacademy.backend.dtos.*;
import com.greenfoxacademy.backend.errors.UserAlreadyExistsError;
import com.greenfoxacademy.backend.models.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

/**
 * Service to manage {@link User} related actions.
 */
@Service
public interface UserService extends UserDetailsService {
  RegisterResponseDto register(RegisterRequestDto userDto) throws UserAlreadyExistsError;

  LoginResponseDto login(LoginRequestDto loginRequestDto) throws Exception;

  ProfileUpdateResponseDto profileUpdate(ProfileUpdateRequestDto profileUpdateRequestDto, User userFromToken) throws Exception;
}
