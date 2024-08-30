package com.greenfoxacademy.backend.services.settings;

import com.greenfoxacademy.backend.dtos.UserSettingsRequestDto;
import com.greenfoxacademy.backend.dtos.UserSettingsResponseDto;

public interface SettingsService {
  UserSettingsResponseDto getSettingsForUser(String username);

  UserSettingsResponseDto addSettingsForUser(String name, UserSettingsRequestDto userSettingsRequestDto);
}
