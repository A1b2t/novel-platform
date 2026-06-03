package com.example.entity;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 用户角色表
 */
@Data
public class UserRole {
    // 主键ID
    private Long id;
    // 用户ID
    private Long userId;
    // 角色编码
    private String roleCode;
    // 创建时间
    private LocalDateTime createTime;
    // 更新时间
    private LocalDateTime updateTime;
    // 逻辑删除
    private Integer deleted;
}
