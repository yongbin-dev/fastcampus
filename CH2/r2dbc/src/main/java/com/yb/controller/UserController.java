package com.yb.controller;

import com.yb.dto.UserCreateRequest;
import com.yb.dto.UserResponse;
import com.yb.dto.UserUpdateRequest;
import com.yb.repository.UserPostResponse;
import com.yb.service.PostService;
import com.yb.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final PostService postService;

    @PostMapping("")
    public Mono<UserResponse> createUser(@RequestBody UserCreateRequest request) {
        return userService.create(request.getName(), request.getEmail()).map(UserResponse::of);
    }

    @GetMapping("")
    public Flux<UserResponse> findAll() {
        return userService.findAll()
                .map(UserResponse::of);
    }

    @GetMapping("/{id}")
    public Mono<UserResponse> findUser(@PathVariable Long id) {
        return userService.findById(id)
                .map(UserResponse::of);
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<?>> deleteUser(@PathVariable Long id) {
        return userService.deleteById(id).then(Mono.just(ResponseEntity.noContent().build()));
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<UserResponse>> updateUser(@PathVariable Long id, @RequestBody UserUpdateRequest userUpdateRequest) {
        return userService.update(id, userUpdateRequest.getName(), userUpdateRequest.getEmail())
                .map(u -> ResponseEntity.ok(UserResponse.of(u)))
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }

    @GetMapping("/{id}/posts")
    public Flux<UserPostResponse> getUserPosts(@PathVariable Long id) {
        return postService.findAllByUserId(id)
                .map(UserPostResponse::of);
    }
}
