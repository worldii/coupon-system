package com.example.api.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CouponCountRepository {

    private static final String COUPON_COUNT = "coupon_count";
    private final RedisTemplate<String, String> redisTemplate;

    public Long increaseCouponCount() {
        return redisTemplate
            .opsForValue()
            .increment(COUPON_COUNT);
    }
}
