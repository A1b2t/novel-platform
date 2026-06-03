package com.example.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 排行榜快照
 */
@Data
public class RankSnapshot {
    // 快照ID
    private Long id;
    // 排行榜类型
    private String rankType;
    // 小说ID
    private Long novelId;
    // 评分
    private BigDecimal score;
    // 快照日期
    private LocalDate snapshotDate;
}
