package com.example.vo.novel;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 小说列表 VO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "小说列表项")
public class NovelListVO {

    @Schema(description = "小说ID")
    private Long id;

    @Schema(description = "小说名称")
    private String novelName;

    @Schema(description = "封面地址")
    private String coverUrl;

    @Schema(description = "简介")
    private String intro;

    @Schema(description = "作者笔名")
    private String authorName;

    @Schema(description = "分类名称")
    private String categoryName;

    @Schema(description = "字数")
    private Long wordCount;

    @Schema(description = "章节数")
    private Integer chapterCount;

    @Schema(description = "状态（0-连载中 1-已完结）")
    private Integer status;

    @Schema(description = "是否VIP（0-免费 1-VIP）")
    private Integer isVip;

    @Schema(description = "阅读量")
    private Long readCount;

    @Schema(description = "收藏量")
    private Integer collectCount;
}
