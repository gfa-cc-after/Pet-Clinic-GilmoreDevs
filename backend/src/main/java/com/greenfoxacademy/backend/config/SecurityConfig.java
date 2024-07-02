package com.greenfoxacademy.backend.config;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;


/**
 * To give security permission to all endpoints.
 */
@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfig {

  /**
   * To give security permission to all endpoints.
   */
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    // @formatter:off
    http
            .authorizeHttpRequests((authorize) -> authorize
                    .anyRequest().permitAll()
            )
            .httpBasic(withDefaults())
            .formLogin(withDefaults());
    // @formatter:on
    return http.build();
  }
}