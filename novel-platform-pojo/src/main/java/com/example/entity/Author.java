package com.example.entity;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 作者表
 */
@Data
public class Author {
    // 作者ID
    private Long id;
    // 关联用户ID
    private Long userId;
    // 笔名
    private String penName;
    // 作者简介
    private String intro;
    // 审核状态
    private Integer status;
    // 创建时间
    private LocalDateTime createTime;
    // 更新时间
    private LocalDateTime updateTime;
    // 逻辑删除
    private Integer deleted;
}
