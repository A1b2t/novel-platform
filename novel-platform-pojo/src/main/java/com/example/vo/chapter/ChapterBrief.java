package com.example.vo.chapter;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 章节简要信息（上下章导航用）
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "章节简要信息")
public class ChapterBrief { //不加vo以示区分

    @Schema(description = "章节ID")
    private Long id;

    @Schema(description = "章节标题")
    private String chapterTitle;
}
