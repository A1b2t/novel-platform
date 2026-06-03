package com.example.entity;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 阅读记录表
 */
@Data
public class ReadingHistory {
    // 阅读记录ID
    private Long id;
    // 用户ID
    private Long userId;
    // 小说ID
    private Long novelId;
    // 章节ID
    private Long chapterId;
    // 最后阅读时间
    private LocalDateTime lastReadTime;
    // 逻辑删除
    private Integer deleted;
}
