package com.yb.couponcore.service;

import com.yb.couponcore.exception.CouponIssueException;
import com.yb.couponcore.exception.ErrorCode;
import com.yb.couponcore.repository.redis.RedisRepository;
import com.yb.couponcore.repository.redis.dto.CouponRedisEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.yb.couponcore.model.QCoupon.coupon;
import static com.yb.couponcore.util.CouponRedisUtils.getIssueRequestKey;

@Service
@RequiredArgsConstructor
public class CouponIssueRedisService {

    private final RedisRepository redisRepository;

    public void checkCouponIssueQuantity(CouponRedisEntity couponRedisEntity  , long userId){
        if(!availableTotalIssueQuantity(couponRedisEntity.totalQuantity(), couponRedisEntity.id())){
            throw new CouponIssueException(ErrorCode.INVALID_COUPON_ISSUE_QUANTITY , ErrorCode.INVALID_COUPON_ISSUE_QUANTITY.message);
        }

        if(!availableUserIssueQuantity(couponRedisEntity.id(), userId)){
            throw new CouponIssueException(ErrorCode.DUPLICATED_COUPON_ISSUE , ErrorCode.DUPLICATED_COUPON_ISSUE.message);
        }
    }

    public boolean availableTotalIssueQuantity(Integer totalQuantity , long couponId){
        if(totalQuantity == null){
            return true;
        }

        String key = getIssueRequestKey(couponId);
        return totalQuantity > redisRepository.sCard(key);
    }

    public boolean availableUserIssueQuantity(long couponId , long userId){
        String key = getIssueRequestKey(couponId);
        return !redisRepository.sIsMember(key , String.valueOf(userId));
    }

}
