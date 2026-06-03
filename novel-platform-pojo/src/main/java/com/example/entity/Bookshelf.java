package com.example.entity;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 书架表
 */
@Data
public class Bookshelf {
    // 书架ID
    private Long id;
    // 用户ID
    private Long userId;
    // 小说ID
    private Long novelId;
    // 创建时间
    private LocalDateTime createTime;
    // 逻辑删除
    private Integer deleted;
}
