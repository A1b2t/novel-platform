package com.example.novelplatformserver.controller.portal.auth;

import com.example.dto.LoginDTO;
import com.example.dto.RegisterDTO;
import com.example.novelplatformserver.service.AuthService;
import com.example.response.Result;
import com.example.vo.LoginVO;
import com.example.vo.UserInfoVO;
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
@RequestMapping("/api/v1/portal/auth")
public class AuthController {

    private final AuthService authService;

    /**
     * 注册
     *
     * @return
     */
    @Operation(summary = "用户注册")
    @RequestMapping("/register")
    public Result<Void> register(@RequestBody @Valid RegisterDTO dto){
        authService.register(dto);
        return Result.success();
    }

    /**
     * 登录
     */
    @Operation(summary = "用户登录")
    @PostMapping("/login")
    public Result<LoginVO> login(@RequestBody @Valid LoginDTO dto){
        LoginVO vo = authService.login(dto);
        return Result.success(vo);
    }

    /**
     * 获取当前用户信息
     */
    @Operation(summary = "获取当前登录用户")
    @GetMapping("/me")
    public Result<UserInfoVO> me(){
        // TODO: 从 Token 中获取当前用户ID
        Long userId = 1L;
        UserInfoVO vo = authService.getCurrentUserInfo(userId);
        return Result.success(vo);
    }

    /**
     * 退出登录
     */
    @Operation(summary = "退出登录")
    @PostMapping("/logout")
    public Result<Void> logout(){
        authService.logout();
        return Result.success();
    }


}
