package com.greenfoxacademy.backend.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * This is the JWT Config.
 */

@Configuration
@Getter
public class JwtConfig {
  @Value("${jwt.expiration-time}")
  private Long expirationTime;
}