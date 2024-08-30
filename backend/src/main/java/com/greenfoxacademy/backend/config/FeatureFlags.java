package com.greenfoxacademy.backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;

@Configuration
@Getter
public class FeatureFlags {

    @Value("${features.emailVerification.enabled}")
    private boolean isEmailVerificationEnabled;

    @Value("${features.emailVerification.enabled}")
    private boolean isSettingsEnabled;
}