package com.greenfoxacademy.backend.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;

/**
 * Represents an Admin entity in the system, extending the {@link User} class.
 * Admins have specific authorities and roles assigned to them for security and access control.
 *
 * <p>This class makes use of Lombok annotations such as {@code @SuperBuilder},
 * {@code @RequiredArgsConstructor}, and {@code @AllArgsConstructor} to generate the necessary
 * constructors and builder patterns.</p>
 *
 * <p>Admins are granted a specific role of "ROLE_ADMIN" through Spring Security's
 * {@link GrantedAuthority} interface.</p>
 *
 * <p>The authority is transient, meaning it is not persisted in the database, but is
 * dynamically assigned at runtime.</p>
 *
 * @see User
 * @see GrantedAuthority
 * @see SimpleGrantedAuthority
 */

@Entity
@SuperBuilder
@RequiredArgsConstructor
@AllArgsConstructor
public class Admin extends User {
  @Override
  @Transient
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"));
  }
}
