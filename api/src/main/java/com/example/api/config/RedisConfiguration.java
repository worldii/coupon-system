package com.example.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@Configuration
@EnableRedisRepositories
public class RedisConfiguration {

    @Bean
    public LettuceConnectionFactory redisConnectionFactory(
        @Value("${spring.data.redis.host}") String redisHost,
        @Value("${spring.data.redis.port}") int redisPort
    ) {
        return new LettuceConnectionFactory(
            redisHost,
            redisPort
        );
    }

    @Bean
    public RedisTemplate<?, ?> redisTemplate(final LettuceConnectionFactory connectionFactory) {
        final RedisTemplate<byte[], byte[]> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);
        return redisTemplate;
    }
}
