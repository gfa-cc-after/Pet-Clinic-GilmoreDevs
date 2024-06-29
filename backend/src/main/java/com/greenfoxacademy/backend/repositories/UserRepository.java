package com.greenfoxacademy.backend.repositories;
import com.greenfoxacademy.backend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Integer> {

}
