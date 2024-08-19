package com.yb.couponcore.repository.mysql;

import com.querydsl.jpa.JPQLQueryFactory;
import com.yb.couponcore.model.CouponIssue;
import com.yb.couponcore.model.QCouponIssue;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.yb.couponcore.model.QCouponIssue.couponIssue;

@RequiredArgsConstructor
@Repository
public class CouponIssueRepository {

    private final JPQLQueryFactory queryFactory;

    public CouponIssue findFirstCouponIssue(long couponId, long userId) {
        return queryFactory.selectFrom(couponIssue)
                .where(couponIssue.couponId.eq(couponId))
                .where(couponIssue.userId.eq(userId))
                .fetchFirst();
    }

}
