package com.greenfoxacademy.backend.services.user;

import com.greenfoxacademy.backend.dtos.LoginRequestDto;
import com.greenfoxacademy.backend.dtos.LoginResponseDto;
import com.greenfoxacademy.backend.dtos.ProfileUpdateRequestDto;
import com.greenfoxacademy.backend.dtos.ProfileUpdateResponseDto;
import com.greenfoxacademy.backend.dtos.RegisterRequestDto;
import com.greenfoxacademy.backend.dtos.RegisterResponseDto;
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

  ProfileUpdateResponseDto profileUpdate(
      String user, ProfileUpdateRequestDto profileUpdateRequestDto) throws Exception;

  public void deleteProfile(String username) throws Exception;
}
