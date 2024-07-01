package com.greenfoxacademy.backend.services;

import com.greenfoxacademy.backend.models.User;
import com.greenfoxacademy.backend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public void register(User newUser) {
        userRepository.save(newUser);
    }
}
