package com.example.entity;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 评论点赞
 */
@Data
public class CommentLike {
    // 点赞ID
    private Long id;
    // 评论ID
    private Long commentId;
    // 用户ID
    private Long userId;
    // 创建时间
    private LocalDateTime createTime;
}
