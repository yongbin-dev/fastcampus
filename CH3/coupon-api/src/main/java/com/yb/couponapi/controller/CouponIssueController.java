package com.yb.couponapi.controller;

import com.yb.couponapi.controller.dto.CouponIssueRequestDto;
import com.yb.couponapi.controller.dto.CouponIssueResponseDto;
import com.yb.couponapi.service.CouponIssueRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class CouponIssueController {

    private final CouponIssueRequestService couponIssueRequestService;

    @PostMapping("/v1/issue")
    public CouponIssueResponseDto issueV1(@RequestBody CouponIssueRequestDto dto){
        couponIssueRequestService.issueRequestV1(dto);
        return new CouponIssueResponseDto(true , null);
    }

    @PostMapping("/v1/issue-async")
    public CouponIssueResponseDto asyncIssueV1(@RequestBody CouponIssueRequestDto dto){
        couponIssueRequestService.asyncIssueRequestV1(dto);
        return new CouponIssueResponseDto(true , null);
    }

    @PostMapping("/v2/issue-async")
    public CouponIssueResponseDto asyncIssueV2(@RequestBody CouponIssueRequestDto dto){
        couponIssueRequestService.asyncIssueRequestV2(dto);
        return new CouponIssueResponseDto(true , null);
    }

}
