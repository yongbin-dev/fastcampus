package com.yb.repository;

import com.yb.entity.Post;
import reactor.core.publisher.Flux;

public interface PostCustomRepository {

    Flux<Post> findAllByUserId(Long userId);
}
