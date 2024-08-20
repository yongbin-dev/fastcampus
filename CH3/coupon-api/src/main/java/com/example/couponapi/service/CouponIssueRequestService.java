package com.example.couponapi.service;

import com.example.couponapi.controller.dto.CouponIssueRequestDto;
import com.yb.couponcore.component.DistributeLockExecutor;
import com.yb.couponcore.service.CouponIssueService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CouponIssueRequestService {

    private final CouponIssueService couponIssueService;
    private final DistributeLockExecutor distributeLockExecutor;

    public void issueRequestV1(CouponIssueRequestDto requestDto) {

        //REDIS 분산락
//        distributeLockExecutor.execute("lock_" + requestDto.couponId(), 10000, 10000, () -> {
//            couponIssueService.issue(requestDto.couponId(), requestDto.userId());
//        });

        //MYSQL 분산락
        couponIssueService.issue(requestDto.couponId(), requestDto.userId());

        log.info("쿠폰 발급 완료. couponId :%s , userId : %s".formatted(requestDto.couponId(), requestDto.userId()));
    }
}
