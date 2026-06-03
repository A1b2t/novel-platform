package com.example.novelplatformserver.service.serviceImpl;

import com.example.novelplatformserver.mapper.UserMapper;
import com.example.novelplatformserver.service.AuthService;
import com.example.dto.LoginDTO;
import com.example.dto.RegisterDTO;
import com.example.entity.User;
import com.example.exception.BusinessException;
import com.example.vo.LoginVO;
import com.example.vo.UserInfoVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * 认证服务实现
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private static final String TOKEN_TYPE = "Bearer";
    private static final Long EXPIRE_TIME = 7200L;

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void register(RegisterDTO dto) {
        // 1. 校验确认密码是否一致
        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            throw new BusinessException("两次密码不一致");
        }

        // 2. 检查用户名是否已存在
        User existingUser = userMapper.selectByUsername(dto.getUsername());
        if (existingUser != null) {
            throw new BusinessException("用户名已存在");
        }

        // 3. 构建用户对象
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setNickname(StringUtils.hasText(dto.getNickname()) ? dto.getNickname() : dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPhone(dto.getPhone());

        // 4. 保存到数据库
        userMapper.insert(user);
        log.info("用户注册成功: {}", user.getUsername());
    }

    @Override
    public LoginVO login(LoginDTO dto) {
        // 1. 根据用户名查询用户
        User user = userMapper.selectByUsername(dto.getUsername());
        if (user == null) {
            throw new RuntimeException("用户名或密码错误");
        }

        // 2. 校验密码
        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new BusinessException("用户名或密码错误");
        }

        // 3. 生成 JWT Token
        // TODO: 引入 JWT 工具类
        String token = "placeholder_token";

        log.info("用户登录成功: {}", user.getUsername());
        return LoginVO.builder()
                .token(token)
                .tokenType(TOKEN_TYPE)
                .expireTime(EXPIRE_TIME)
                .build();
    }

    @Override
    public UserInfoVO getCurrentUserInfo(Long userId) {
        // 1. 查询用户
        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }

        // 2. 组装 VO
        return UserInfoVO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .nickname(user.getNickname())
                .avatar(user.getAvatar())
                .email(user.getEmail())
                .phone(user.getPhone())
                .vipStatus(user.getVipStatus())
                .build();
    }

    @Override
    public void logout() {
        log.info("用户退出登录");
        // TODO: 如果 Token 存了 Redis，在这里清除
    }
}
