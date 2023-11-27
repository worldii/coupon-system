package com.example.api.producer;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CouponCreateProducer {

    public static final String COUPON_CREATE = "coupon-create";
    private final KafkaTemplate<String, Long> kafkaTemplate;

    public void createCoupon(final Long userId) {
        kafkaTemplate.send(COUPON_CREATE, userId);
    }
}
