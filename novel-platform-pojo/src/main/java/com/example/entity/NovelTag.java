package com.example.entity;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 标签表
 */
@Data
public class NovelTag {
    // 标签ID
    private Long id;
    // 标签名称
    private String tagName;
    // 创建时间
    private LocalDateTime createTime;
    // 更新时间
    private LocalDateTime updateTime;
    // 逻辑删除
    private Integer deleted;
}
