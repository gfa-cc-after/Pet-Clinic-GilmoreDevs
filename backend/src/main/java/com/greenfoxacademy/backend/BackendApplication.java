package com.greenfoxacademy.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * This is the main class of the application.
 */
@SpringBootApplication
@EnableCaching
public class BackendApplication {
  
  public static void main(String[] args) {
    SpringApplication.run(BackendApplication.class, args);
  }
  
}
