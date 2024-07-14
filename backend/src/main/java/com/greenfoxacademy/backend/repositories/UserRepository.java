package com.greenfoxacademy.backend.repositories;

import com.greenfoxacademy.backend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository to manage {@link User} entities.
 */
public interface UserRepository extends JpaRepository<User, Integer> {
  boolean existsByEmail(String email);
  Optional<User> findByEmail(String email);
}
