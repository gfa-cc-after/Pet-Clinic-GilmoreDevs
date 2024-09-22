package com.greenfoxacademy.backend.config;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.oauth2.server.resource.web.access.BearerTokenAccessDeniedHandler;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * To give security permission to all endpoints.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {
  private final CorsConfig corsConfig;
  private final RsaSecretKeys rsaSecretKeys;

  private final String[] allowedUrls = {
      "/register",
      "/login",
      "/health-check",
      "/swagger-ui",
      "/v3/api-docs",
      "/verification"
  };

  /**
   * To create securityFilterChange.
   */

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    // @formatter:off
    http
              .authorizeHttpRequests((authorize) -> authorize
                      .requestMatchers(allowedUrls).permitAll()
                      .requestMatchers("/profile-update").authenticated()
                      .requestMatchers("/pets").authenticated()
                      .requestMatchers("/add-pet").authenticated()
                      .anyRequest().authenticated()
              )
              .cors(cors -> cors.configurationSource(corsConfigurationSource()))
              .csrf((csrf) -> csrf.ignoringRequestMatchers("/login", "/register"))
              .httpBasic(Customizer.withDefaults())
              .oauth2ResourceServer(oauth -> oauth.jwt(jwt -> jwt.decoder(jwtDecoder())))
              .sessionManagement((session) -> session
                  .sessionCreationPolicy(SessionCreationPolicy.STATELESS))

              .exceptionHandling((exceptions) -> exceptions
                      .authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint())
                      .accessDeniedHandler(new BearerTokenAccessDeniedHandler())
        );
    // @formatter:on
    return http.build();
  }

  @Bean
  JwtEncoder jwtEncoder() {
    JWK jwk = new RSAKey
        .Builder(this.rsaSecretKeys.getPublicKey())
        .privateKey(this.rsaSecretKeys.getPrivateKey())
        .build();
    JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
    return new NimbusJwtEncoder(jwks);
  }

  @Bean
  public JwtDecoder jwtDecoder() {
    return NimbusJwtDecoder.withPublicKey(rsaSecretKeys.getPublicKey()).build();
  }

  /**
   * To give security permission to all endpoints.
   */

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(Arrays.stream(corsConfig.getCorsUrls().split(",")).toList());
    configuration
        .setAllowedMethods(Arrays.asList(
            "GET",
            "POST",
            "PUT",
            "PATCH",
            "DELETE",
            "OPTIONS",
            "HEAD"
        ));
    configuration.setAllowedHeaders(List.of("*"));
    configuration.setAllowCredentials(true);

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }
}

