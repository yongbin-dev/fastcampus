package com.yb.couponcore.repository.redis;

import com.yb.couponcore.exception.CouponIssueException;
import com.yb.couponcore.exception.ErrorCode;

public enum CouponIssueRequestCode {

    SUCCESS(1),
    DUPLICATED_COUPON_ISSUE(2),
    INVALID_COUPON_ISSUE_QUANTITY(3);

    CouponIssueRequestCode(int code){

    }

    public static CouponIssueRequestCode find(String code){
        int codeValue = Integer.parseInt(code);
        if(codeValue == 1) return SUCCESS;
        if(codeValue == 2) return DUPLICATED_COUPON_ISSUE;
        if(codeValue == 3) return INVALID_COUPON_ISSUE_QUANTITY;
        throw new IllegalArgumentException("존재하지 않는 코드입니다.");
    }

    public static void checkRequestResult(CouponIssueRequestCode code){
        if(code == INVALID_COUPON_ISSUE_QUANTITY){
            throw new CouponIssueException(ErrorCode.INVALID_COUPON_ISSUE_QUANTITY , ErrorCode.INVALID_COUPON_ISSUE_QUANTITY.message);
        }

        if(code == DUPLICATED_COUPON_ISSUE) {
            throw new CouponIssueException(ErrorCode.DUPLICATED_COUPON_ISSUE , ErrorCode.DUPLICATED_COUPON_ISSUE.message);
        }
    }

}
