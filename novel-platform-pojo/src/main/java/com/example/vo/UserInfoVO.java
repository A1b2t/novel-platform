package com.example.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "当前登录用户信息")
public class UserInfoVO {

    @Schema(description = "用户ID")
    private Long id;

    @Schema(description = "用户名", example = "novel_fan")
    private String username;

    @Schema(description = "昵称", example = "小说迷")
    private String nickname;

    @Schema(description = "头像")
    private String avatar;

    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "手机号")
    private String phone;

    @Schema(description = "会员状态")
    private Integer vipStatus;

    @Schema(description = "角色列表")
    private List<String> roles;
}
