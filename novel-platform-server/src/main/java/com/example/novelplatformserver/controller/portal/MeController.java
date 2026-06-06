package com.example.novelplatformserver.controller.portal;

import com.example.context.UserContext;
import com.example.novelplatformserver.service.AuthService;
import com.example.response.Result;
import com.example.vo.UserInfoVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 前台 - 当前用户信息接口
 */
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/portal")
@Tag(name = "前台用户信息接口")
public class MeController {

    private final AuthService authService;

    @Operation(summary = "获取当前登录用户信息")
    @GetMapping("/me")
    public Result<UserInfoVO> me() {
        Long userId = UserContext.getUserId();
        log.info("current user id = {}", userId);
        UserInfoVO vo = authService.getCurrentUserInfo(userId);
        return Result.success(vo);
    }
}
