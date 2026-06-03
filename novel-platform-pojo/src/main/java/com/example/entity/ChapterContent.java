package com.example.entity;

import lombok.Data;

/**
 * 章节内容表
 */
@Data
public class ChapterContent {
    // 内容ID
    private Long id;
    // 章节ID
    private Long chapterId;
    // 章节内容
    private String content;
}
