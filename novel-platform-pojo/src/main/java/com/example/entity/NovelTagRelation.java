package com.example.entity;

import lombok.Data;

/**
 * 小说标签关联
 */
@Data
public class NovelTagRelation {
    // 关联ID
    private Long id;
    // 小说ID
    private Long novelId;
    // 标签ID
    private Long tagId;
}
