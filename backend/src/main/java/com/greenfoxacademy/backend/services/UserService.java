package com.greenfoxacademy.backend.services;

import com.greenfoxacademy.backend.models.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    public void register(User newUser);

}
