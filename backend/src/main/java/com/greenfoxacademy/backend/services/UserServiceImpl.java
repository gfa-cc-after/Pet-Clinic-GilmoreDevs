package com.greenfoxacademy.backend.services;

import com.greenfoxacademy.backend.dtos.RegisterResponseDto;
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
    private final ModelMapper modelMapper;

    @Override
    public RegisterResponseDto register(RegisterUserDto userDto) throws Exception {
        User user = mapToEntity(userDto);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        return mapToRegisterResponseDto(userRepository.save(user));
    }

    private RegisterResponseDto mapToRegisterResponseDto(User user) {
        return new RegisterResponseDto(user.getId());
    }

    private User mapToEntity(RegisterUserDto userDto) {
        return modelMapper.map(userDto, User.class);
    }
}