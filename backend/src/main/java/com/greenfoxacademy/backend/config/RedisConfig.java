package com.greenfoxacademy.backend.config;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * RedisConfig is a configuration class that defines the cache management strategy
 * for Redis in the Spring Boot application. It configures the Redis Cache Manager
 * and sets key serialization and expiration policies.
 */
@Configuration
@ConditionalOnProperty(name = "spring.cache.type", havingValue = "redis")
public class RedisConfig {
  /**
   * The cacheManager method creates a RedisCacheManager bean that is used to manage.
   */
  @Bean
  public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
    RedisCacheConfiguration cacheConfig = RedisCacheConfiguration.defaultCacheConfig()
            .entryTtl(Duration.ofMinutes(3)) // Set TTL to 5 minutes
            .disableCachingNullValues()
            .serializeKeysWith(
                    RedisSerializationContext
                            .SerializationPair
                            .fromSerializer(new StringRedisSerializer())
            );

    return RedisCacheManager.builder(redisConnectionFactory)
            .cacheDefaults(cacheConfig)
            .build();
  }
}