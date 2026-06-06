package com.example.novelplatformserver.controller.auth;


import com.example.dto.LoginDTO;
import com.example.dto.RegisterDTO;
import com.example.novelplatformserver.service.AuthService;
import com.example.response.Result;
import com.example.vo.LoginVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * 注册
 * 登录
 * 刷新token
 * 退出登录
 */

@RestController
@Slf4j
@RequiredArgsConstructor
@Tag(name = "门户认证接口", description = "用户登录注册相关接口")
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    /**
     * 注册
     *
     * @return
     */
    @Operation(summary = "用户注册接口")
    @PostMapping("/register")
    public Result<Void> register(@RequestBody @Valid RegisterDTO dto){
        authService.register(dto);
        return Result.success();
    }

    /**
     * 登录
     */
    @Operation(summary = "用户登录接口")
    @PostMapping("/login")
    public Result<LoginVO> login(@RequestBody @Valid LoginDTO dto){
        LoginVO vo = authService.login(dto);
        return Result.success(vo);
    }

    /**
     * 退出登录
     */
    @Operation(summary = "退出登录接口")
    @PostMapping("/logout")
    public Result<Void> logout(@RequestHeader("Authorization") String authorization){
        log.info("logout authorization: [{}]", authorization);
        // 兼容带/不带Bearer前缀的情况
        String token = authorization.startsWith("Bearer ")
                ? authorization.substring(7)
                : authorization;
        authService.logout(token);
        return Result.success();
    }

}
