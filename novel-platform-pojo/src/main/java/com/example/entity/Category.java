package com.example.entity;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 小说分类表
 */
@Data
public class Category {
    // 分类ID
    private Long id;
    // 分类名称
    private String categoryName;
    // 排序
    private Integer sort;
    // 状态
    private Integer status;
    // 创建时间
    private LocalDateTime createTime;
    // 更新时间
    private LocalDateTime updateTime;
    // 逻辑删除
    private Integer deleted;
}
