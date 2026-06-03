package com.example.entity;

import lombok.Data;

/**
 * 轮播图
 */
@Data
public class Banner {
    // 轮播图ID
    private Long id;
    // 标题
    private String title;
    // 图片地址
    private String imageUrl;
    // 跳转地址
    private String jumpUrl;
    // 排序
    private Integer sort;
    // 状态
    private Integer status;
}
