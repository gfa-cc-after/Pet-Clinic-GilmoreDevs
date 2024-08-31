package com.greenfoxacademy.backend.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a pet entity in the system. This class is a data model
 * that maps to the pet table in the database.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "_pet")
public class Pet {

  @Id
  @GeneratedValue
  private Integer id;

  @Column(nullable = false)
  private String petName;
  private String petBreed;
  private String petSex;
  private Date petBirthDate;
  private Date lastCheckUp;
  private Date nextCheckUp;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "petOwner_Id")
  private User petOwner;
}
