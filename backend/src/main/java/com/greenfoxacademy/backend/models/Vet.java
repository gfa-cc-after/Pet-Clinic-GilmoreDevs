package com.greenfoxacademy.backend.models;

import jakarta.persistence.*;

import java.util.Collection;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

/**
 * Represents a veterinarian who is also a user in the system.
 * <p>
 * The {@code Vet} class extends the {@code User} class and adds specific information related to
 * a veterinarian, such as the clinic address. It is annotated with {@code @Entity} to indicate
 * that it is a JPA entity and will be mapped to a database table.
 * </p>
 * <p>
 * This class is annotated with {@code @Table(name = "_vet")} to specify the name of the database
 * table to which this entity is mapped. The table name is prefixed with an underscore to avoid
 * conflicts with reserved SQL keywords.
 * </p>
 * <p>
 * The {@code getAuthorities} method overrides the method from {@code User} to provide specific
 * authorities for a veterinarian, in this case, granting the role {@code ROLE_VET}.
 * </p>
 *
 * @see User
 */

@SuperBuilder
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_vet")
public class Vet extends User {
  @Embedded
  private ClinicDetails clinicDetails;

  @Override
  @Transient
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority("ROLE_VET"));
  }
}
