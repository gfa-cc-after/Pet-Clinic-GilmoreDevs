package com.greenfoxacademy.backend.services.auth;

import com.greenfoxacademy.backend.models.User;

public interface AuthService {
  String generateToken(User user);

}
