package com.example.entity;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 评论表
 */
@Data
public class Comment {
    // 评论ID
    private Long id;
    // 用户ID
    private Long userId;
    // 小说ID
    private Long novelId;
    // 评论内容
    private String content;
    // 点赞数
    private Integer likeCount;
    // 状态
    private Integer status;
    // 创建时间
    private LocalDateTime createTime;
    // 更新时间
    private LocalDateTime updateTime;
    // 逻辑删除
    private Integer deleted;
}
