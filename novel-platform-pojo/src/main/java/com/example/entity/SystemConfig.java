package com.example.entity;

import lombok.Data;

/**
 * 系统配置
 */
@Data
public class SystemConfig {
    // 配置ID
    private Long id;
    // 配置键
    private String configKey;
    // 配置值
    private String configValue;
    // 备注
    private String remark;
}
