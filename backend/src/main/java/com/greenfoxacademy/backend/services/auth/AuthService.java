package com.greenfoxacademy.backend.services.auth;

import com.greenfoxacademy.backend.models.User;
import org.springframework.stereotype.Service;

public interface AuthService {
  String generateToken(User user);

}
