package com.yb.webflux.service;

import com.yb.client.PostClient;
import com.yb.webflux.dto.PostResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    // webClient mvc server request

    private final PostClient postClient;

    public Mono<PostResponse> getPostContent(Long id){
        return postClient.getPost(id)
                .onErrorResume(error -> Mono.just(new PostResponse(id.toString() , String.format("Fallback Data %d", id))));
    }

    public Flux<PostResponse> getMultiplePostContent(List<Long> idList) {
        return Flux.fromIterable(idList)
                .flatMap(this::getPostContent)
                .log();
    }

    public Flux<PostResponse> getParallelMultiplePostContent(List<Long> idList) {
        return Flux.fromIterable(idList)
                .parallel()
                .runOn(Schedulers.parallel())
                .flatMap(this::getPostContent)
                .log()
                .sequential();

    }
}
