package com.yb.couponconsumer.listener;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@EnableScheduling
@Component
public class CouponIssueListener {

//    private final CouponIssueService couponIssueService;
//    private final RedisRepository redisRepository;
//    private final ObjectMapper objectMapper = new ObjectMapper();
//    private final String issueRequestQueueKey = getIssueRequestQueueKey();
//    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());
//
//    @Scheduled(fixedDelay = 1000)
//    public void issue() throws JsonProcessingException {
//        log.info("listen...");
//        while (existCouponIssueTarget()) {
//            CouponIssueRequest target = getIssueTarget();
//            log.info("발급 시작 target: " + target);
//            couponIssueService.issue(target.couponId(), target.userId());
//            log.info("발급 완료 target: " + target);
//            removeIssuedTarget();
//        }
//    }
//
//    private boolean existCouponIssueTarget() {
//        return redisRepository.lSize(issueRequestQueueKey) > 0;
//    }
//
//    private CouponIssueRequest getIssueTarget() throws JsonProcessingException {
//        return objectMapper.readValue(redisRepository.lIndex(issueRequestQueueKey, 0), CouponIssueRequest.class);
//    }
//
//    private void removeIssuedTarget() {
//        redisRepository.lPop(issueRequestQueueKey);
//    }
}
