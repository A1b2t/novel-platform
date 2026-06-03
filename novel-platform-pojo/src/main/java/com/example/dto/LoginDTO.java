package com.example.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 登录请求参数
 */
@Data
@Schema(description = "登录请求参数")
public class LoginDTO {

    @NotBlank(message = "用户名不能为空")
    @Schema(description = "用户名", example = "novel_fan")
    /*避免出现用户明明注册成功，登录被 DTO 拦截，所以和RegisterDTO做了区分*/
    private String username;

    @NotBlank(message = "密码不能为空")
    @Size(min = 6, max = 32, message = "密码长度必须在6-32位之间")
    @Schema(description = "密码", example = "123456")
    private String password;
}
