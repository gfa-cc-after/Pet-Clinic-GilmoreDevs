package com.greenfoxacademy.backend.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Id;
import lombok.Data;

@Embeddable
@Data
public class ClinicAddress {
  @Id
  private Long id;
  private String city;
  @Column(length = 4)
  private int zip;
  private String street;
  private String clinicName;


}
