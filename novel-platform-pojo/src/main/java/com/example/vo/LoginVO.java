package com.example.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "登录返回对象")
public class LoginVO {

    @Schema(description = "JWT Token")
    private String token;


    /*
    * 为什么加，因为以后：
    *   Authorization:
    *   Bearer xxxxxxxxx
    * 前端直接：
    * Authorization=login.tokenType+" "+login.token更规范
    */
    @Schema(description = "Token类型", example = "Bearer")
    private String tokenType;

    @Schema(description = "过期时间（秒）", example = "7200")
    private Long expireTime;
}
