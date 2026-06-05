package com.example.vo.chapter;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 章节阅读 VO（读者端，包含上下章导航）
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "章节阅读信息")
public class ChapterReadVO {

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

    @Schema(description = "章节内容")
    private String content;

    @Schema(description = "章节类型（0-免费 1-收费），对应 Chapter 实体字段")
    private Integer chapterType;

    @Schema(description = "是否会员作品（来自 Novel 表）")
    private Integer isVip;

    @Schema(description = "上一章标题")
    private String prevChapterTitle;

    @Schema(description = "下一章标题")
    private String nextChapterTitle;

    @Schema(description = "上一章ID（无则 null）")
    private Long prevChapterId;

    @Schema(description = "下一章ID（无则 null）")
    private Long nextChapterId;

    /*@Schema(description = "上一章序号")
    private Integer prevChapterNo;

    @Schema(description = "下一章序号")
    private Integer nextChapterNo;*/
}
