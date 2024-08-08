package com.yb.repository;

import com.yb.entity.User;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveCrudRepository<User, Long> {
    Flux<User> findByName(String name);
    Flux<User> findByNameOrderByIdDesc(String name);

    @Modifying
    @Query("DELETE FROM users WHERE name = :name")
    Mono<Void> deleteByName(String name);
}
