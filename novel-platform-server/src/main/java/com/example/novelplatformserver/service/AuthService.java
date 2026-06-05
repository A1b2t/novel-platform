package com.example.novelplatformserver.service;

import com.example.dto.LoginDTO;
import com.example.dto.RegisterDTO;
import com.example.vo.LoginVO;
import com.example.vo.UserInfoVO;

/**
 * 认证服务接口
 */
public interface AuthService {

    /**
     * 用户注册
     */
    void register(RegisterDTO dto);

    /**
     * 用户登录
     */
    LoginVO login(LoginDTO dto);

    /**
     * 获取当前登录用户信息
     */
    UserInfoVO getCurrentUserInfo(Long userId);

    /**
     * 退出登录
     */
    void logout(String token);
}
