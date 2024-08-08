package com.yb.service;

import com.yb.entity.Post;
import com.yb.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class PostService {
    // webClient mvc server request

    private final PostRepository postRepository;

    public Mono<Post> create(Long id, String title, String content) {
        return postRepository.save(Post.builder()
                .userId(id)
                .title(title)
                .content(content)
                .build()
        );
    }

    public Flux<Post> findAll() {
        return postRepository.findAll();
    }

    public Mono<Post> findById(Long id) {
        return postRepository.findById(id);
    }

    public Mono<Void> deleteById(Long id) {
        return postRepository.deleteById(id);
    }


    public Flux<Post> findAllByUserId(Long id) {
        return postRepository.findAllByUserId(id);
    }
}
