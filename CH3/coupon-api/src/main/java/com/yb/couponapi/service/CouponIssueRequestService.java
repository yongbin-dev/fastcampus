package com.yb.couponapi.service;

import com.yb.couponapi.controller.dto.CouponIssueRequestDto;
import com.yb.couponcore.component.DistributeLockExecutor;
import com.yb.couponcore.service.AsyncCouponIssueServiceV1;
import com.yb.couponcore.service.AsyncCouponIssueServiceV2;
import com.yb.couponcore.service.CouponIssueService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CouponIssueRequestService {

    private final CouponIssueService couponIssueService;
    private final AsyncCouponIssueServiceV1 asyncCouponIssueServiceV1;
    private final AsyncCouponIssueServiceV2 asyncCouponIssueServiceV2;

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


    public void asyncIssueRequestV1(CouponIssueRequestDto requestDto) {
        asyncCouponIssueServiceV1.issue(requestDto.couponId(), requestDto.userId());
        log.info("쿠폰 발급 완료. couponId :%s , userId : %s".formatted(requestDto.couponId(), requestDto.userId()));
    }

    public void asyncIssueRequestV2(CouponIssueRequestDto requestDto) {
        asyncCouponIssueServiceV2.issue(requestDto.couponId(), requestDto.userId());
        log.info("쿠폰 발급 완료. couponId :%s , userId : %s".formatted(requestDto.couponId(), requestDto.userId()));
    }
}
