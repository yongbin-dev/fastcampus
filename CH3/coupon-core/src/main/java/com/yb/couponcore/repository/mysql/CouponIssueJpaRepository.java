package com.yb.couponcore.repository.mysql;

import com.yb.couponcore.model.Coupon;
import com.yb.couponcore.model.CouponIssue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponIssueJpaRepository extends JpaRepository<CouponIssue, Long> {
}
