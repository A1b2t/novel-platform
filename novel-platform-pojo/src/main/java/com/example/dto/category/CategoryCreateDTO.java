package com.example.dto.category;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 创建分类 DTO
 */
@Data
@Schema(description = "创建分类参数")
public class CategoryCreateDTO {

    @NotBlank(message = "分类名称不能为空")
    @Size(min = 1, max = 50, message = "分类名称长度1-50")
    @Schema(description = "分类名称")
    private String categoryName;

    @Schema(description = "排序（越小越靠前）")
    private Integer sort = 0;

    @Schema(description = "状态（0-禁用 1-启用），默认1")
    private Integer status = 1;
}
