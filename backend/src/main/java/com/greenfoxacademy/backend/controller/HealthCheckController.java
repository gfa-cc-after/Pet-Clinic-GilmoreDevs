package com.greenfoxacademy.backend.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Endpoint created to see if app works
 */
@Controller
public class HealthCheckController {
  @GetMapping ("/health-check")
  public ResponseEntity<String> healthCheck() {
    return new ResponseEntity<>("OK", HttpStatus.OK);
  }
  
}
