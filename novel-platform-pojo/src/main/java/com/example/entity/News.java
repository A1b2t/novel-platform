package com.example.entity;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 新闻公告
 */
@Data
public class News {
    // 新闻ID
    private Long id;
    // 标题
    private String title;
    // 内容
    private String content;
    // 状态
    private Integer status;
    // 创建时间
    private LocalDateTime createTime;
}
