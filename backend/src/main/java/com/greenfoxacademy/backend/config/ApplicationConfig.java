package com.greenfoxacademy.backend.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
/**
 * Configuration class for setting up application-wide beans.
 */
@Configuration
public class ApplicationConfig {
  @Bean
  public ModelMapper modelMapper() {
    return new ModelMapper();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
