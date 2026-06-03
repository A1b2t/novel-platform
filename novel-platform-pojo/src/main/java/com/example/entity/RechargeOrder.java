package com.example.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 充值订单
 */
@Data
public class RechargeOrder {
    // 订单ID
    private Long id;
    // 订单编号
    private String orderNo;
    // 用户ID
    private Long userId;
    // 金额
    private BigDecimal amount;
    // 状态
    private Integer status;
    // 创建时间
    private LocalDateTime createTime;
}
