package com.example.entity;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 小说表
 */
@Data
public class Novel {
    // 小说ID
    private Long id;
    // 作者ID
    private Long authorId;
    // 分类ID
    private Long categoryId;
    // 小说名称
    private String novelName;
    // 封面地址
    private String coverUrl;
    // 简介
    private String intro;
    // 总字数
    private Long wordCount;
    // 章节数
    private Integer chapterCount;
    // 状态
    private Integer status;
    // 是否会员作品
    private Integer isVip;
    // 收藏数
    private Integer collectCount;
    // 评论数
    private Integer commentCount;
    // 阅读量
    private Long readCount;
    // 创建时间
    private LocalDateTime createTime;
    // 更新时间
    private LocalDateTime updateTime;
    // 逻辑删除
    private Integer deleted;
}
