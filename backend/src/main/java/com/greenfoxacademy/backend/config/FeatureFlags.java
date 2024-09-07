package com.greenfoxacademy.backend.config;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class FeatureFlags {

  @Value("${features.emailVerificationEnabled}")
  private boolean emailVerificationEnabled;
}