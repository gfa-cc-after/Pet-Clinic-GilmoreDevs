package com.greenfoxacademy.backend.services.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;

@Service
public class RateLimiterService {

  @Autowired
  private StringRedisTemplate redisTemplate;

  private static final int OWNER_RATE_LIMIT = 5;
  private static final int ADMIN_RATE_LIMIT = 20;

  // Method to determine rate limit based on role
  public boolean isRateLimited(String email, List<String> roles) {
    int maxRequestsPerMinute = getMaxRequestsPerMinute(roles);

    String key = "rate_limit:" + email;
    Long requestCount = redisTemplate.opsForValue().increment(key);

    if (requestCount == 1) {
      redisTemplate.expire(key, Duration.ofMinutes(1));
    }

    return requestCount > maxRequestsPerMinute;
  }

  // Helper method to determine rate limit based on user role
  private int getMaxRequestsPerMinute(List<String> roles) {
    if (roles.contains("ROLE_ADMIN")) {
      return ADMIN_RATE_LIMIT;
    }
    return OWNER_RATE_LIMIT;  // Default rate limit for regular users
  }
}