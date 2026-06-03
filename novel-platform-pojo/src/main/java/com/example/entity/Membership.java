package com.example.entity;

import lombok.Data;
import java.math.BigDecimal;

/**
 * 会员表
 */
@Data
public class Membership {
    // 会员ID
    private Long id;
    // 会员名称
    private String membershipName;
    // 价格
    private BigDecimal price;
    // 天数
    private Integer duration;
}
