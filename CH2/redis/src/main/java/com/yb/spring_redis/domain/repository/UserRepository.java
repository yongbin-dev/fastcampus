package com.yb.spring_redis.domain.repository;


import com.yb.spring_redis.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
