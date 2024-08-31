package com.greenfoxacademy.backend.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Entity
@Data
public class Address {
  @Id
  private Long id;
  private String city;
  @Column(length = 4)
  private String zip;
  private String street;
  private String clinicName;


}
