package com.example.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 每日统计
 */
@Data
public class StatisticsDaily {
    // 统计ID
    private Long id;
    // 统计日期
    private LocalDate statisticsDate;
    // 新增用户数
    private Integer newUserCount;
    // 阅读量
    private Long readCount;
    // 收入
    private BigDecimal income;
}
