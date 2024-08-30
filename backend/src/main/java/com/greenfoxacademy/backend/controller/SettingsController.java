package com.greenfoxacademy.backend.controller;

import java.security.Principal;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ConditionalOnExpression("${features.settings.enabled:false}")
@RequestMapping(value = "/api/v1/settings", produces = MediaType.APPLICATION_JSON_VALUE)
public class SettingsController {

  @GetMapping("/")
  public ResponseEntity<?> getSettings(Principal principal) {
    return new ResponseEntity<>("hello", HttpStatus.OK);
  }

}
