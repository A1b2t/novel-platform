package com.example.vo.chapter;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 章节详情 VO（作者后台用，包含完整信息）
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "章节详情")
public class ChapterDetailVO {

    @Schema(description = "章节ID")
    private Long id;

    @Schema(description = "小说ID")
    private Long novelId;
    @Schema(description = "小说名称")
    private String novelName;

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

    @Schema(description = "章节内容")
    private String content;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
}
