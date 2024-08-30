package com.greenfoxacademy.backend.models;


import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "_settings")
@Data
@Getter
@Setter
public class Settings {
  @Id
  @GeneratedValue
  private Long id;

  @Column(name = "accent_color")
  private String accentColor;

  @OneToOne
  private User user;
}
