package com.greenfoxacademy.backend.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * This configuration is to setup the email sending services.
 */
@Configuration
@Getter
public class EmailConfiguration {

  @Value("${email.baseUrl}")
  private String baseUrl;
}
