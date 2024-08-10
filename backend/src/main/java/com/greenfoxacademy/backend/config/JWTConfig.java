package com.greenfoxacademy.backend.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class JWTConfig {
  @Value("${jwt.expiration-time}")
  private Long expirationTime;
}