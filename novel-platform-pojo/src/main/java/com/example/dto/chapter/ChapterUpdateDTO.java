package com.example.dto.chapter;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

/**
 * 修改章节 DTO
 */
@Data
@Schema(description = "修改章节参数")
public class ChapterUpdateDTO {

    @NotBlank(message = "章节标题不能为空")
    @Size(min = 1, max = 200, message = "章节标题长度1-200")
    @Schema(description = "章节标题")
    private String chapterTitle;

    @Min(value = 0, message = "章节类型错误")
    @Max(value = 1, message = "章节类型错误")
    @Schema(description = "章节类型（0-免费 1-收费）")
    private Integer chapterType;

    @NotBlank(message = "章节内容不能为空")
    @Size(max = 2000000, message = "章节内容过长")
    @Schema(description = "章节内容")
    private String content;
}
