package com.yb.couponcore.component;

import com.yb.couponcore.model.event.CouponIssueCompleteEvent;
import com.yb.couponcore.service.CouponCacheService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@RequiredArgsConstructor
@Component
@Slf4j
public class CouponEventListener {

    private final CouponCacheService cacheService;

    @TransactionalEventListener(phase =  TransactionPhase.AFTER_COMMIT)
    void issueComplete(CouponIssueCompleteEvent event){
        log.info("issue complete , cache refresh start CouponId : %s".formatted(event.couponId()));
        cacheService.putCouponCache(event.couponId());
        cacheService.putCouponLocalCache(event.couponId());
        log.info("issue complete , cache refresh end CouponId : %s".formatted(event.couponId()));
    }
}
