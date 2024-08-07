package com.yb.spring_redis.domain.service;


import com.yb.spring_redis.config.CacheConfig;
import com.yb.spring_redis.domain.repository.RedisHashUserRepository;
import com.yb.spring_redis.domain.repository.UserRepository;
import com.yb.spring_redis.domain.entity.RedisHashUser;
import com.yb.spring_redis.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RedisHashUserRepository redisHashUserRepository;

    private final RedisTemplate<String, User> userRedisTemplate;
    private final RedisTemplate<String, Object> objectRedisTemplate;

    public User getUser(final Long id) {
//        var key = "users:%d".formatted(id);
//        var cachedUser = objectRedisTemplate.opsForValue().get(key);
//        if (cachedUser != null) {
//            return (User) cachedUser;
//        }
        User user = userRepository.findById(id).orElseThrow();
//        userRedisTemplate.opsForValue().set(key , user , Duration.ofSeconds(30));
//        objectRedisTemplate.opsForValue().set(key, user, Duration.ofSeconds(30));
        return user;
    }

    public RedisHashUser getUser2(final Long id) {
        var cachedUser = redisHashUserRepository.findById(id).orElseGet(() -> {
            User user = userRepository.findById(id).orElseThrow();
            return redisHashUserRepository.save(RedisHashUser.builder()
                    .id(user.getId())
                    .name(user.getName())
                    .email(user.getEmail())
                    .createdAt(user.getCreatedAt())
                    .updatedAt(user.getUpdatedAt())
                    .build());
        });
        return cachedUser;
    }

    @Cacheable(cacheNames = CacheConfig.CACHE1, key = "'user:' + #p0")
    public User getUser3(final Long id) {
        return userRepository.findById(id).orElseThrow();
    }
}
