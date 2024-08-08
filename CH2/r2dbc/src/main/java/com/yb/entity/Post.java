package com.yb.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@Table("posts")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    @Id
    private Long id;

    @Column("user_id")
    private Long userId;

    private String title;

    private String content;

    @Transient
    private User user;

    @Column("created_at")
    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column("updated_at")
    private LocalDateTime updatedAt;

}
