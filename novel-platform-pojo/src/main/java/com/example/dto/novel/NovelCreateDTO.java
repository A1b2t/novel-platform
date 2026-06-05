package com.example.dto.novel;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 创建小说 DTO
 */
@Data
@Schema(description = "创建小说参数")
public class NovelCreateDTO {

    @NotNull(message = "分类不能为空")
    @Schema(description = "分类ID")
    private Long categoryId;

    @NotBlank(message = "小说名称不能为空")
    @Size(min = 1, max = 100, message = "小说名称长度1-100")
    @Schema(description = "小说名称")
    private String novelName;

    @Size(max = 255, message = "封面地址过长")
    @Schema(description = "封面地址")
    private String coverUrl;

    @Size(max = 2000, message = "简介不能超过2000字")
    @Schema(description = "简介")
    private String intro;

    @Schema(description = "是否会员作品")
    private Integer isVip = 0;
}


