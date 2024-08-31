package com.greenfoxacademy.backend.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Represents a user entity in the system. This class is a data model
 * that maps to the user table in the database.
 */
@Data
@SuperBuilder
@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
public abstract class User implements UserDetails {

  @Id
  @GeneratedValue
  private Integer id;
  private String firstName;
  private String lastName;
  @Column(unique = true)
  private String email;
  private String password;
  private UUID verificationId;

  @OneToMany(mappedBy = "petOwner", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<Pet> pets = new ArrayList<>();


  @Override
  public String getUsername() {
    return email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return this.verificationId == null;
  }
}