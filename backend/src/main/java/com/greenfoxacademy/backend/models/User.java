package com.greenfoxacademy.backend.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a user entity in the system. This class is a data model
 * that maps to the user table in the database
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "_user")
public class User {

  @Id
  @GeneratedValue
  private Integer id;
  private String firstName;
  private String lastName;
  private String email;
  private String password;


}
