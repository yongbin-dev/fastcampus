package com.yb.couponcore.repository.mysql;

import com.yb.couponcore.model.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponJpaRepository extends JpaRepository<Coupon , Long> {
}
