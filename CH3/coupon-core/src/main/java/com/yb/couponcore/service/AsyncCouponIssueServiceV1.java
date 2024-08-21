package com.yb.couponcore.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yb.couponcore.component.DistributeLockExecutor;
import com.yb.couponcore.exception.CouponIssueException;
import com.yb.couponcore.exception.ErrorCode;
import com.yb.couponcore.model.Coupon;
import com.yb.couponcore.repository.redis.RedisRepository;
import com.yb.couponcore.repository.redis.dto.CouponIssueRequest;
import lombok.RequiredArgsConstructor;
import org.checkerframework.common.value.qual.StringVal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.yb.couponcore.util.CouponRedisUtils.getIssueRequestKey;
import static com.yb.couponcore.util.CouponRedisUtils.getIssueRequestQueueKey;

@Service
@RequiredArgsConstructor
public class AsyncCouponIssueServiceV1 {

    private final RedisRepository redisRepository;
    private final CouponIssueRedisService couponIssueRedisService;
    private final CouponIssueService couponIssueService;

    private final DistributeLockExecutor distributeLockExecutor;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public void issue(long couponId , long userId){
        Coupon coupon = couponIssueService.findCoupon(couponId);

        if(!coupon.availableIssueDate()){
            throw new CouponIssueException(ErrorCode.INVALID_COUPON_ISSUE_DATE , ErrorCode.INVALID_COUPON_ISSUE_DATE.message);
        }

        distributeLockExecutor.execute("lock_%s".formatted(couponId) , 3000 , 3000 , ()->{
            if(!couponIssueRedisService.availableTotalIssueQuantity(coupon.getTotalQuantity(), couponId)){
                throw new CouponIssueException(ErrorCode.INVALID_COUPON_ISSUE_QUANTITY , ErrorCode.INVALID_COUPON_ISSUE_QUANTITY.message);
            }

            if(!couponIssueRedisService.availableUserIssueQuantity(couponId, userId)){
                throw new CouponIssueException(ErrorCode.DUPLICATED_COUPON_ISSUE , ErrorCode.DUPLICATED_COUPON_ISSUE.message);
            }

            issueRequest(couponId , userId);
        });
    }

    private void issueRequest(long couponId , long userId){
        CouponIssueRequest couponIssueRequest = new CouponIssueRequest(couponId , userId);
        try{
            String value = objectMapper.writeValueAsString(couponIssueRequest);

            redisRepository.sAdd(getIssueRequestKey(couponId) , String.valueOf(userId));
            redisRepository.rPush(getIssueRequestQueueKey(), value );
        }catch(JsonProcessingException e){
            throw new CouponIssueException(ErrorCode.FAIL_COUPON_ISSUE_REQUEST , ErrorCode.FAIL_COUPON_ISSUE_REQUEST.message);
        }
    }
}
