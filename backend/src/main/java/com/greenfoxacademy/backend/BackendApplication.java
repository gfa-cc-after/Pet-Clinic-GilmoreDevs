package com.greenfoxacademy.backend;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import com.greenfoxacademy.backend.config.OllamaConfig;

import io.github.ollama4j.OllamaAPI;
import io.github.ollama4j.types.OllamaModelType;
import lombok.RequiredArgsConstructor;

/**
 * This is the main class of the application.
 */
@SpringBootApplication
@EnableCaching
@RequiredArgsConstructor
public class BackendApplication implements CommandLineRunner {
  private final OllamaConfig config;

  public static void main(String[] args) {
    SpringApplication.run(BackendApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    OllamaAPI ollamaAPI = new OllamaAPI(config.getOllamaUrl());
    ollamaAPI.pullModel("llama3.1");
  }

}
