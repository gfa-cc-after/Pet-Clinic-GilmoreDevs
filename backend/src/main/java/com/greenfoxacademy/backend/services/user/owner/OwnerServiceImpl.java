package com.greenfoxacademy.backend.services.user.owner;

import com.greenfoxacademy.backend.config.FeatureFlags;
import com.greenfoxacademy.backend.dtos.LoginRequestDto;
import com.greenfoxacademy.backend.dtos.LoginResponseDto;
import com.greenfoxacademy.backend.dtos.ProfileUpdateRequestDto;
import com.greenfoxacademy.backend.dtos.ProfileUpdateResponseDto;
import com.greenfoxacademy.backend.dtos.RegisterRequestDto;
import com.greenfoxacademy.backend.dtos.RegisterResponseDto;
import com.greenfoxacademy.backend.errors.CannotUpdateUserException;
import com.greenfoxacademy.backend.errors.UserAlreadyExistsError;
import com.greenfoxacademy.backend.models.Owner;
import com.greenfoxacademy.backend.repositories.OwnerRepository;
import com.greenfoxacademy.backend.services.auth.AuthService;
import com.greenfoxacademy.backend.services.mail.EmailService;
import jakarta.transaction.Transactional;

import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service implementation to manage {@link OwnerService}.
 */
@Service
@RequiredArgsConstructor
public class OwnerServiceImpl implements OwnerService {
  private final OwnerRepository ownerRepository;
  private final PasswordEncoder passwordEncoder;
  private final AuthService authService;
  private final EmailService emailService;
  private final FeatureFlags featureFlags;

  @Override
  public RegisterResponseDto register(RegisterRequestDto registerRequestDto)
          throws UserAlreadyExistsError {

    boolean isEmailEnabled = featureFlags.isEmailVerificationEnabled();
    UUID verificationId = isEmailEnabled ? UUID.randomUUID() : null;
    // @formatter:off
    Owner owner = Owner.builder()
            .email(registerRequestDto.email())
            .firstName(registerRequestDto.firstName())
            .lastName(registerRequestDto.lastName())
            .password(passwordEncoder.encode(registerRequestDto.password()))
            .verificationId(verificationId)
            .build();
    // @formatter:on
    try {
      Owner saved = ownerRepository.save(owner);
      if (isEmailEnabled) {
        emailService.sendRegistrationEmail(
                saved.getEmail(),
                saved.getFirstName(),
                saved.getVerificationId());
      }
      return new RegisterResponseDto(saved.getId());
    } catch (Exception e) {
      throw new UserAlreadyExistsError("Email is already taken!");
    }
  }

  @Override
  public LoginResponseDto login(LoginRequestDto loginRequestDto) throws Exception {
    Owner owner = ownerRepository.findByEmail(loginRequestDto.email())
            .orElseThrow(() -> new Exception("User not found"));
    if (!owner.isEnabled()) {
      throw new Exception("User's email is not verified");
    } else if (!passwordEncoder.matches(loginRequestDto.password(), owner.getPassword())) {
      throw new Exception("Invalid password");
    }
    return new LoginResponseDto(authService.generateToken(owner));
  }

  @CacheEvict(value = "update-profile-cache", key = "#email")
  @Override
  public ProfileUpdateResponseDto profileUpdate(
          String email,
          ProfileUpdateRequestDto profileUpdateRequestDto
  ) throws CannotUpdateUserException {
    Owner owner = ownerRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    if (
            ownerRepository.existsByEmail(profileUpdateRequestDto.email())
                    && !email.equals(profileUpdateRequestDto.email())
    ) {
      throw new CannotUpdateUserException("Email is already taken!");
    }
    owner.setEmail(profileUpdateRequestDto.email());
    owner.setFirstName(profileUpdateRequestDto.firstName());
    owner.setLastName(profileUpdateRequestDto.lastName());
    owner.setPassword(passwordEncoder.encode(profileUpdateRequestDto.password()));

    Owner updatedUser = ownerRepository.save(owner);
    return new ProfileUpdateResponseDto(authService.generateToken(updatedUser));
  }

  @Cacheable(value = "profile-cache", key = "#username")
  @Override
  public Owner findByEmail(String username) throws UsernameNotFoundException {
    return ownerRepository.findByEmail(username)
            .orElseThrow(() -> new UsernameNotFoundException("No such user!"));
  }

  /**
   * Delete the user by username.
   */
  @CacheEvict(value = "delete-cache", key = "#username")
  @Transactional
  @Override
  public void deleteProfile(String username) {
    ownerRepository.deleteByEmail(username);
  }

  /**
   * Verify the user by id sent as email.
   */
  public void verifyUser(UUID id) {
    Owner userWithId = ownerRepository.findByVerificationId(id).orElseThrow();
    if (!featureFlags.isEmailVerificationEnabled()) {
      return;
    }
    userWithId.setVerificationId(null);
    ownerRepository.save(userWithId);
  }

  /**
   * Locates the user based on the username. In the actual implementation, the search
   * may possibly be case sensitive, or case insensitive depending on how the
   * implementation instance is configured. In this case, the <code>UserDetails</code>
   * object that comes back may have a username that is of a different case than what
   * was actually requested..
   *
   * @param username the username identifying the user whose data is required.
   * @return a fully populated user record (never <code>null</code>)
   * @throws UsernameNotFoundException if the user could not be found or the user has no
   *                                   GrantedAuthority
   */
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return ownerRepository.findByEmail(username)
      .orElseThrow(() -> new UsernameNotFoundException("No such user!"));
  }
}