package com.greenfoxacademy.backend.services.settings;

import com.greenfoxacademy.backend.dtos.UserSettingsRequestDto;
import com.greenfoxacademy.backend.dtos.UserSettingsResponseDto;
import com.greenfoxacademy.backend.models.Settings;
import com.greenfoxacademy.backend.models.User;
import com.greenfoxacademy.backend.repositories.SettingsRepository;
import com.greenfoxacademy.backend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class SettingsServiceImpl implements SettingsService {
  private final SettingsRepository settingsRepository;
  private final UserRepository userRepository;

  @Override
  public UserSettingsResponseDto getSettingsForUser(String email) {
    Settings settings = settingsRepository.findByUserEmail(email).orElseThrow();
    return new UserSettingsResponseDto(settings.getAccentColor());
  }

  @Override
  public UserSettingsResponseDto addSettingsForUser(String email, UserSettingsRequestDto userSettingsRequestDto) {
    User user = userRepository.findByEmail(email).orElseThrow();
    Settings settingsOfUser = user.getUserSettings();
    if (settingsOfUser == null) {
      Settings settings = new Settings();
      settings.setAccentColor(userSettingsRequestDto.accentColor());
      settingsRepository.save(settings);
      user.updateSettings(settings);
    } else {
      settingsOfUser.setAccentColor(userSettingsRequestDto.accentColor());
    }
    User savedWithSettings = userRepository.save(user);
    return new UserSettingsResponseDto(savedWithSettings.getUserSettings().getAccentColor());
  }

}
