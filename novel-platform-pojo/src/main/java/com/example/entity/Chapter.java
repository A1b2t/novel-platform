package com.example.entity;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 章节表
 */
@Data
public class Chapter {
    // 章节ID
    private Long id;
    // 小说ID
    private Long novelId;
    // 章节序号
    private Integer chapterNo;
    // 章节标题
    private String chapterTitle;
    // 字数
    private Integer wordCount;
    // 章节类型
    private Integer chapterType;
    // 发布状态
    private Integer publishStatus;
    // 创建时间
    private LocalDateTime createTime;
    // 更新时间
    private LocalDateTime updateTime;
    // 逻辑删除
    private Integer deleted;
}
