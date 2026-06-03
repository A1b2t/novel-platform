package com.example.entity;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 收藏表
 */
@Data
public class Favorite {
    // 收藏ID
    private Long id;
    // 用户ID
    private Long userId;
    // 小说ID
    private Long novelId;
    // 创建时间
    private LocalDateTime createTime;
}
