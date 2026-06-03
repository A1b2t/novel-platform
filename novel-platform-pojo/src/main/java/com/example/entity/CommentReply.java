package com.example.entity;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 评论回复
 */
@Data
public class CommentReply {
    // 回复ID
    private Long id;
    // 评论ID
    private Long commentId;
    // 用户ID
    private Long userId;
    // 回复内容
    private String replyContent;
    // 创建时间
    private LocalDateTime createTime;
    // 逻辑删除
    private Integer deleted;
}
