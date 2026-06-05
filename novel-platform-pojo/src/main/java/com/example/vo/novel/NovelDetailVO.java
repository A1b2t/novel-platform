package com.example.vo.novel;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 小说详情 VO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "小说详情")
public class NovelDetailVO {

    @Schema(description = "小说ID")
    private Long id;

    @Schema(description = "作者ID")
    private Long authorId;

    @Schema(description = "作者昵称")
    private String authorName;

    @Schema(description = "分类ID")
    private Long categoryId;

    @Schema(description = "分类名称")
    private String categoryName;

    @Schema(description = "小说名称")
    private String novelName;

    @Schema(description = "封面地址")
    private String coverUrl;

    @Schema(description = "简介")
    private String intro;

    @Schema(description = "总字数")
    private Long wordCount;

    @Schema(description = "章节数")
    private Integer chapterCount;

    @Schema(description = "小说状态")
    private Integer status;

    @Schema(description = "是否会员作品")
    private Integer isVip;

    @Schema(description = "收藏数")
    private Integer collectCount;

    @Schema(description = "评论数")
    private Integer commentCount;

    @Schema(description = "阅读量")
    private Long readCount;
    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
}
