package com.example.api.integration;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.example.api.service.ApplyService;
import com.example.api.repository.CouponRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import({ApplyService.class})
@DisplayName("ApplyService 통합 테스트")
public class ApplyServiceTest {

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
}
