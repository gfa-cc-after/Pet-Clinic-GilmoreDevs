package com.greenfoxacademy.backend.services.auth;

import com.greenfoxacademy.backend.models.User;
/**
 * To give parameter type to generate Token.
 */

public interface AuthService {
  String generateToken(User user);

}
