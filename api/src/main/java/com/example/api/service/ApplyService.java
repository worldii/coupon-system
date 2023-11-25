package com.example.api.service;

import com.example.api.domain.Coupon;
import com.example.api.repository.CouponCountRepository;
import com.example.api.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApplyService {

    private static final long COUPON_LIMIT = 100L;
    private final CouponCountRepository couponCountRepository;
    private final CouponRepository couponRepository;

    public Long applyCoupon(final Long userId) {
        final Long count = couponCountRepository.increaseCouponCount();
        if (count > COUPON_LIMIT) {
            throw new IllegalStateException("쿠폰은 100개까지만 발급 가능합니다.");
        }

        final Coupon coupon = couponRepository.save(new Coupon(userId));
        return coupon.getId();
    }
}
