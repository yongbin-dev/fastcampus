package com.yb.couponcore.service;

import com.yb.couponcore.exception.CouponIssueException;
import com.yb.couponcore.exception.ErrorCode;
import com.yb.couponcore.model.Coupon;
import com.yb.couponcore.model.CouponIssue;
import com.yb.couponcore.repository.mysql.CouponIssueJpaRepository;
import com.yb.couponcore.repository.mysql.CouponIssueRepository;
import com.yb.couponcore.repository.mysql.CouponJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CouponIssueService {

    private final CouponJpaRepository couponJpaRepository;
    private final CouponIssueJpaRepository couponIssueJpaRepository;
    private final CouponIssueRepository couponIssueRepository;

    @Transactional
    public void issue(long couponId , long userId){
        Coupon coupon = findCouponWithLock(couponId);
        coupon.issue();
        saveCouponIssue(couponId, userId);
    }

    @Transactional(readOnly = true)
    public Coupon findCoupon(long couponId){
        return couponJpaRepository.findById(couponId).orElseThrow(()->{
            throw new CouponIssueException(ErrorCode.COUPON_NOT_EXIST, "쿠폰 정책이 존재하지 않습니다. %s".formatted(couponId));
        });
    }

    @Transactional(readOnly = true)
    public Coupon findCouponWithLock(long couponId){
        return couponJpaRepository.findCouponWithLock(couponId).orElseThrow(()->{
            throw new CouponIssueException(ErrorCode.COUPON_NOT_EXIST, "쿠폰 정책이 존재하지 않습니다. %s".formatted(couponId));
        });
    }

    @Transactional
    public CouponIssue saveCouponIssue(long couponId, long userId) {
        checkAlreadyIssuance(couponId , userId);
        CouponIssue issue = CouponIssue.builder()
                .couponId(couponId)
                .userId(userId)
                .build();

        return couponIssueJpaRepository.save(issue);
    }

    public void checkAlreadyIssuance(long couponId , long userId){
        CouponIssue issue = couponIssueRepository.findFirstCouponIssue(couponId , userId);
        if(issue != null){
            throw new CouponIssueException(ErrorCode.DUPLICATED_COUPON_ISSUE , "이미 발급된 쿠폰입니다.%s %s".formatted(userId , couponId));
        }
    }

}
