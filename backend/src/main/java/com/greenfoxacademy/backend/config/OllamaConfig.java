package com.greenfoxacademy.backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;

@Configuration
@Getter
public class OllamaConfig {

    @Value("${ollama.url}")
    private String ollamaUrl;
}
