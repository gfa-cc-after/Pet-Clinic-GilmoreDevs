package com.greenfoxacademy.backend.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for managing email-related settings.
 * <p>
 * This class is used to load email-related properties from the application's configuration files,
 * such as the base URL used for email verification links.
 * It leverages Spring's {@link Configuration}
 * and {@link Value} annotations to inject configuration properties into the class.
 * </p>
 */
@Configuration
@Getter
public class EmailConfiguration {

  @Value("${email.baseUrl}")
  private String baseUrl;
}
