package com.greenfoxacademy.backend.services;

import com.greenfoxacademy.backend.models.User;
import com.greenfoxacademy.backend.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    UserRepository userRepository;


    @Override
    public void register(User newUser) {
        userRepository.save(newUser);
    }
}
