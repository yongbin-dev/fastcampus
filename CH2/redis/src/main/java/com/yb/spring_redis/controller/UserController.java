package com.yb.spring_redis.controller;

import com.yb.spring_redis.domain.entity.RedisHashUser;
import com.yb.spring_redis.domain.entity.User;
import com.yb.spring_redis.domain.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/users/{id}")
    public User getUser(@PathVariable("id") Long id) {
        return userService.getUser(id);
    }

    @GetMapping("/redishash-users/{id}")
    public RedisHashUser getUser2(@PathVariable("id") Long id) {
        return userService.getUser2(id);
    }

    @GetMapping("/caching-users/{id}")
    public User getUser3(@PathVariable("id") Long id) {
        return userService.getUser3(id);
    }

    @GetMapping("/session")
    public Map<String , String> home (HttpSession session) {
        Integer visitCount = (Integer) session.getAttribute("visits");
        if(visitCount == null){
            visitCount = 0;
        }
        session.setAttribute("visits" , ++visitCount);
        return Map.of("session id ", session.getId() , "visits" , visitCount.toString());
    }

}
