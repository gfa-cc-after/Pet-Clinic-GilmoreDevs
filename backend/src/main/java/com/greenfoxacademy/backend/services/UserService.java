package com.greenfoxacademy.backend.services;

import com.greenfoxacademy.backend.dtos.RegisterResponseDto;
import com.greenfoxacademy.backend.dtos.RegisterUserDto;
import com.greenfoxacademy.backend.models.User;
import org.springframework.stereotype.Service;

/**
 * Service to manage {@link User} related actions.
 */
@Service
public interface UserService {
    RegisterResponseDto register(RegisterUserDto userDto) throws Exception;
}
