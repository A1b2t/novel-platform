package com.example.entity;

import lombok.Data;
import java.math.BigDecimal;

/**
 * 推荐表
 */
@Data
public class Recommend {
    // 推荐ID
    private Long id;
    // 小说ID
    private Long novelId;
    // 推荐类型
    private String recommendType;
    // 评分
    private BigDecimal score;
}
