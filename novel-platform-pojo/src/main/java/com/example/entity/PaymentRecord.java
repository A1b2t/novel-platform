package com.example.entity;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 支付记录
 */
@Data
public class PaymentRecord {
    // 记录ID
    private Long id;
    // 订单编号
    private String orderNo;
    // 支付类型
    private String payType;
    // 支付状态
    private Integer payStatus;
    // 创建时间
    private LocalDateTime createTime;
}
