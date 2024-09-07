package com.greenfoxacademy.backend.models;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.util.Collection;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

/**
 * Represents an owner in the system who is also a user.
 * <p>
 * The {@code Owner} class extends the {@code User} class and includes specific information related
 * to an owner, such as the list of pets they own. It is marked with {@code @Entity} to indicate
 * that it is a JPA entity and is mapped to a database table.
 * </p>
 * <p>
 * The {@code @Table(name = "_owner")} annotation specifies the name of the database table
 * associated with this entity. The table name is prefixed with an underscore to avoid conflicts
 * with reserved SQL keywords.
 * </p>
 * <p>
 * The {@code getAuthorities} method overrides the method from {@code User} to provide specific
 * authorities for an owner, granting the role {@code ROLE_OWNER}.
 * </p>
 *
 * @see User
 */

@Entity
@SuperBuilder
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "_owner")
public class Owner extends User {
  @OneToMany(mappedBy = "petOwner")
  private List<Pet> pets;

  @Override
  @Transient
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority("ROLE_OWNER"));
  }
}
