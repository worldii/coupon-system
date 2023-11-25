package com.example.api.config;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import redis.embedded.RedisServer;

@TestConfiguration
public class TestRedisConfiguration {
    @Bean
    public LettuceConnectionFactory redisConnectionFactory(
        @Value("${spring.data.redis.port}") final int redisPort,
        @Value("${spring.data.redis.host}") final String redisHost
    ) {
        return new LettuceConnectionFactory(redisHost, redisPort);
    }

    @Bean
    public RedisTemplate<?, ?> redisTemplate(final LettuceConnectionFactory connectionFactory) {
        final RedisTemplate<byte[], byte[]> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);
        return redisTemplate;
    }

    private final RedisServer redisServer;

    public TestRedisConfiguration() throws IOException {
        this.redisServer = new RedisServer(6379);
    }

    @PostConstruct
    public void postConstruct() {
        redisServer.start();
    }

    @PreDestroy
    public void preDestroy() {
        redisServer.stop();
    }
}
