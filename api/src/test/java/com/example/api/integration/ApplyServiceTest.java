package com.example.api.integration;

import static java.util.concurrent.Executors.newFixedThreadPool;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.example.api.config.TestRedisConfiguration;
import com.example.api.repository.CouponCountRepository;
import com.example.api.repository.CouponRepository;
import com.example.api.service.ApplyService;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DisplayName("ApplyService 통합 테스트")
@DataJpaTest
@Import({TestRedisConfiguration.class, ApplyService.class, CouponCountRepository.class})
class ApplyServiceTest {

    @Autowired
    private ApplyService applyService;

    @Autowired
    private CouponRepository couponRepository;

    @Test
    @DisplayName("쿠폰을 한개만 발급한다")
    void issueCoupon() {
        // given
        final Long userId = 1L;

        // when
        final Long count = applyService.applyCoupon(userId);

        // then
        assertThat(count).isEqualTo(1L);
    }

    @Test
    @DisplayName("여러개의 쿠폰을 발급한다")
    void issueCoupons() throws InterruptedException {
        // given
        final int threadCount = 1000;
        final int nThreads = 32;
        final ExecutorService executorService = newFixedThreadPool(nThreads);
        final CountDownLatch countDownLatch = new CountDownLatch(threadCount);
        final int expectedCount = 100;

        // when
        for (int i = 0; i < threadCount; i++) {
            Long userId = (long) i;
            executorService.submit(() -> {
                try {
                    applyService.applyCoupon(userId);
                } finally {
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await();

        // then
        final Long count = couponRepository.count();
        assertThat(count).isEqualTo(expectedCount);
    }
}
