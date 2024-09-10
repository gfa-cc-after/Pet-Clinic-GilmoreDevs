package com.greenfoxacademy.backend.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for feature flags.
 */
@Getter
@Configuration
public class FeatureFlags {

  /**
   * Flag to enable email verification.
   */
  @Value("${features.emailVerificationEnabled}")
  private boolean emailVerificationEnabled;
}