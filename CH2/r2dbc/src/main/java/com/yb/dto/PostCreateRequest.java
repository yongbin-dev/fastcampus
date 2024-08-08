package com.yb.dto;

import lombok.Data;

@Data
public class PostCreateRequest {
    private Long userId;
    private String title;
    private String content;
}
