package com.example.api.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class RedisProperties {
    private int redisPort;
    private String redisHost;

    public RedisProperties(
      final @Value("${spring.data.redis.port}") int redisPort,
      final @Value("${spring.data.redis.host}") String redisHost) {
        this.redisPort = redisPort;
        this.redisHost = redisHost;
    }
}
