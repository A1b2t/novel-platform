package com.example.vo.chapter;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 章节列表 VO（不含内容，轻量）
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "章节列表项")
public class ChapterListVO {

    @Schema(description = "章节ID")
    private Long id;

    @Schema(description = "章节序号")
    private Integer chapterNo;

    @Schema(description = "章节标题")
    private String chapterTitle;

    @Schema(description = "字数")
    private Integer wordCount;

    @Schema(description = "章节类型（0-免费 1-收费）")
    private Integer chapterType;

    @Schema(description = "发布状态（0-草稿 1-已发布 2-下架）")
    private Integer publishStatus;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;   //应该是不用看发布时间的
}
