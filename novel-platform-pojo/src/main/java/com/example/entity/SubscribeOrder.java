package com.example.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 订阅订单
 */
@Data
public class SubscribeOrder {
    // 订单ID
    private Long id;
    // 用户ID
    private Long userId;
    // 小说ID
    private Long novelId;
    // 章节ID
    private Long chapterId;
    // 金额
    private BigDecimal amount;
    // 创建时间
    private LocalDateTime createTime;
}
