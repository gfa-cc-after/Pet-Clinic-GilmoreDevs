package com.greenfoxacademy.backend.services;

import com.greenfoxacademy.backend.models.User;
import org.springframework.stereotype.Service;

/**
 * Service to manage {@link User} related actions.
 */
@Service
public interface UserService {
  void register(User newUser);

}
