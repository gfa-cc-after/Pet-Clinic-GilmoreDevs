package com.greenfoxacademy.backend.controller;

import java.security.Principal;

import com.greenfoxacademy.backend.dtos.UserSettingsRequestDto;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.greenfoxacademy.backend.services.settings.SettingsService;

import lombok.RequiredArgsConstructor;

@RestController
@ConditionalOnExpression("${features.settings.enabled:false}")
@RequestMapping(value = "/api/v1/settings", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class SettingsController {
  private final SettingsService settingsService;

  @GetMapping("/")
  public ResponseEntity<?> getSettings(Principal principal) {
    return new ResponseEntity<>(settingsService.getSettingsForUser(principal.getName()), HttpStatus.OK);
  }

  @PostMapping("/")
  public ResponseEntity<?> addSettings(Principal principal, @RequestBody UserSettingsRequestDto userSettingsRequestDto) {
    return new ResponseEntity<>(settingsService.addSettingsForUser(principal.getName(), userSettingsRequestDto), HttpStatus.OK);
  }

}
