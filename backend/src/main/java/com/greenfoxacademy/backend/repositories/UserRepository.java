package com.greenfoxacademy.backend.repositories;

import com.greenfoxacademy.backend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository to manage {@link User} entities
 */
public interface UserRepository extends JpaRepository<User, Integer> {

}
