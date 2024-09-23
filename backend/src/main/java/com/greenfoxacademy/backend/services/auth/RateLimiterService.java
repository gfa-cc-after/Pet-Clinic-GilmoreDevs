package com.greenfoxacademy.backend.services.auth;

import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
public class RateLimiterService {

  private static final int OWNER_RATE_LIMIT = 5;
  private static final int ADMIN_RATE_LIMIT = 20;

  public RateLimiterService() {
    JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
    jedisConnectionFactory.afterPropertiesSet();
    StringRedisTemplate redisTemplate = new StringRedisTemplate(jedisConnectionFactory);
  }

  // Method to determine rate limit based on role
  public boolean isRateLimited(UserDetails userDetails, String userAuthority) {
    int maxRequestsPerMinute = getMaxRequestsPerMinute(userAuthority);

    String key = "ratelimit:" + userDetails.getUsername() + ":requests";
    String atKey = redisTemplate.opsForValue().get(key);

    if (atKey == null) {
      redisTemplate.opsForValue().set(key, "0", 1, TimeUnit.MINUTES);
      return false;
    }

    int requests = Integer.parseInt(Objects.requireNonNull(atKey));
    if (requests >= maxRequestsPerMinute) {
      return true;
    }

    redisTemplate.opsForValue().increment(key);
    return false;
  }

  // Helper method to determine rate limit based on user role
  private int getMaxRequestsPerMinute(String userAuthority) {
    if (userAuthority.equals("ROLE_ADMIN")) {
      return ADMIN_RATE_LIMIT;
    }
    return OWNER_RATE_LIMIT;
  }
}