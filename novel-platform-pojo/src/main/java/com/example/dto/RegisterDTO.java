package com.example.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 注册请求参数
 */
@Data
@Schema(description = "注册请求参数")
public class RegisterDTO {

    @NotBlank(message = "用户名不能为空")
    @Pattern(regexp = "^[a-zA-Z0-9_]{4,20}$", message = "用户名只能包含字母、数字、下划线，4-20位")
    @Schema(description = "用户名", example = "novel_fan")
    private String username;

    @Size(max = 20, message = "昵称最长20位")
    @Schema(description = "昵称（默认等于用户名）", example = "小说迷")
    private String nickname;

    @NotBlank(message = "密码不能为空")
    @Size(min = 6, max = 32, message = "密码长度在6-32之间")
    @Schema(description = "密码", example = "123456")
    private String password;

    @NotBlank(message = "确认密码不能为空")
    @Schema(description = "确认密码", example = "123456")
    private String confirmPassword;

    @Pattern(regexp = "^$|^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "邮箱格式错误")
    @Schema(description = "邮箱", example = "user@example.com")
    private String email;

    @Pattern(regexp = "^$|^1[3-9]\\d{9}$", message = "手机号格式错误")
    @Schema(description = "手机号", example = "13800138000")
    private String phone;
}
