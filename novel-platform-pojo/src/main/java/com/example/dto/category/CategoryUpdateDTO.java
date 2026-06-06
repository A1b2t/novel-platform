package com.example.dto.category;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 修改分类 DTO
 */
@Data
@Schema(description = "修改分类参数")
public class CategoryUpdateDTO {

    @NotBlank(message = "分类名称不能为空")
    @Size(min = 1, max = 50, message = "分类名称长度1-50")
    @Schema(description = "分类名称")
    private String categoryName;

    @Schema(description = "排序（越小越靠前）")
    private Integer sort;

    @Schema(description = "状态（0-禁用 1-启用）")
    private Integer status;
}
