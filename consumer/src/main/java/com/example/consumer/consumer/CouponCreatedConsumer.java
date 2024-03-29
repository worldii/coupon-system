package com.example.consumer.consumer;

import com.example.consumer.domain.Coupon;
import com.example.consumer.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CouponCreatedConsumer {

    private final CouponRepository couponRepository;
    @KafkaListener(topics = "coupon_create", groupId = "consumer-group")
    public void consume(Long couponId) {
        couponRepository.save(new Coupon(couponId));
    }
}
