package com.yb.service;

import com.yb.entity.User;
import com.yb.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final ReactiveRedisTemplate<String, User> reactiveRedisTemplate;

    public Mono<User> create(String name, String email) {
        return userRepository.save(User.builder().name(name).email(email).build());
    }

    public Flux<User> findAll() {
        return userRepository.findAll();
    }

    private String getUserCacheKey(Long id) {
        return "users:%d".formatted(id);
    }

    public Mono<User> findById(Long id) {
        // 1. redis 조회
        // 2. 값이 존재하면 응답
        // 3. 없다면 DB에 저장하고 그 결과를 redis 에 저장
        return reactiveRedisTemplate.opsForValue().get(getUserCacheKey(id))
                .switchIfEmpty(userRepository.findById(id).flatMap(u -> reactiveRedisTemplate
                        .opsForValue()
                        .set(getUserCacheKey(id), u , Duration.ofSeconds(30))
                        .then(Mono.just(u)))
                );
    }

    public Mono<Void> deleteById(Long id) {
        return userRepository.deleteById(id);
    }

    public Mono<User> update(Long id, String name, String email) {
        return findById(id).flatMap(u -> {
                    u.setName(name);
                    u.setEmail(email);
                    return userRepository.save(u);
                })
                .flatMap(u -> reactiveRedisTemplate.unlink(getUserCacheKey(id)).then(Mono.just(u)));
    }

}
