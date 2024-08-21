package com.yb.couponapi;

import com.yb.couponapi.controller.dto.CouponIssueResponseDto;
import com.yb.couponcore.exception.CouponIssueException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CouponControllerAdvice {

    @ExceptionHandler(CouponIssueException.class)
    public CouponIssueResponseDto couponIssueExceptionHandler(CouponIssueException exception){
        return new CouponIssueResponseDto(false , exception.getErrorCode().message);
    }
}
