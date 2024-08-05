package com.yb.webflux.respository;

import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final ConcurrentHashMap<Long, User> userHashMap = new ConcurrentHashMap<>();
    private AtomicLong sequence = new AtomicLong(1L);

    @Override
    public Mono<User> save(User user) {
        LocalDateTime dateTime = LocalDateTime.now();

        if (user.getId() == null) {
            user.setId(sequence.getAndAdd(1));
            user.setCreatedAt(dateTime);
        }

        user.setUpdatedAt(dateTime);
        userHashMap.put(user.getId(), user);
        return Mono.just(user);
    }

    @Override
    public Flux<User> findAll() {
        return Flux.fromIterable(userHashMap.values());
    }

    @Override
    public Mono<User> findById(Long id) {
        return Mono.justOrEmpty(userHashMap.getOrDefault(id, null));
    }

    @Override
    public Mono<Integer> deleteById(Long id) {
        User user = userHashMap.getOrDefault(id, null);
        if (user == null) {
            return Mono.just(0);
        }

        userHashMap.remove(id);
        return Mono.just(1);
    }
}
