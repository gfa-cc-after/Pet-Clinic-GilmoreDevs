package com.greenfoxacademy.backend.config;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class JwtConfigTest {

  @Autowired
  private JwtConfig jwtConfig = new JwtConfig();

  @Test
  void getExpirationTime() {
    assertEquals(86400000, jwtConfig.getExpirationTime());
  }
}