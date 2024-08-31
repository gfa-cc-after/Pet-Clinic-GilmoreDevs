package com.greenfoxacademy.backend.models;

import jakarta.persistence.Entity;

import java.util.List;

@Entity
public class Owner extends User {
  List<Pet> pets;
}
