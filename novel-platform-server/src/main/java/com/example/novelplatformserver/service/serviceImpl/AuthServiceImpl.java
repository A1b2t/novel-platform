package com.example.novelplatformserver.service.serviceImpl;

import com.example.novelplatformserver.mapper.UserMapper;
import com.example.novelplatformserver.mapper.UserRoleMapper;
import com.example.novelplatformserver.service.AuthService;
import com.example.novelplatformserver.service.TokenService;
import com.example.novelplatformserver.utils.JwtUtil;
import com.example.constant.RoleConstant;
import com.example.dto.LoginDTO;
import com.example.dto.RegisterDTO;
import com.example.entity.User;
import com.example.entity.UserRole;
import com.example.exception.BusinessException;
import com.example.vo.LoginVO;
import com.example.vo.UserInfoVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 认证服务实现
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private static final String TOKEN_TYPE = "Bearer";
    private final UserMapper userMapper;
    private final UserRoleMapper userRoleMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final TokenService tokenService;

    @Value("${jwt.expiration}")
    private Long expireTime;

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
        /*学习了一下，.encode()方法大概就是：
        1.生成随机盐值    2.固定p、s两个数组（p、s盒，Blowfish算法的pi常熟）
        3.用盐值异或p盒...    4.执行 2^strength轮的加密算法
        5.最终编码成字符串（应该16进制）
        * */
        user.setPassword(passwordEncoder.encode(dto.getPassword()));//加密密码再存库（BCrypt加密存储）
        user.setNickname(StringUtils.hasText(dto.getNickname()) ? dto.getNickname() : dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPhone(dto.getPhone());

        // 4. 保存用户
        userMapper.insert(user);

        // 5. 分配默认角色
        UserRole role = new UserRole();
        role.setUserId(user.getId());   //现在有值了
        role.setRoleCode(RoleConstant.USER);
        userRoleMapper.insert(role);
        log.info("用户注册成功, id:{}, username:{}", user.getId(), user.getUsername());
    }

    @Override
    public LoginVO login(LoginDTO dto) {
        // 1. 根据用户名查询用户
        User user = userMapper.selectByUsername(dto.getUsername());
        if (user == null) {
            throw new BusinessException("用户名或密码错误");
        }

        // 2. 校验密码
        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new BusinessException("用户名或密码错误");
        }

        // 3. 查询角色
        List<UserRole> userRoles = userRoleMapper.selectByUserId(user.getId());
        String role = userRoles.stream()
                .map(UserRole::getRoleCode)
                .collect(Collectors.joining(","));

        // 4. 生成 JWT Token
        String token = jwtUtil.generateToken(user.getId(), user.getUsername(), role);
        log.info("用户登录成功, id:{}, username:{}", user.getId(), user.getUsername());
        return LoginVO.builder()
                .token(token)
                .tokenType(TOKEN_TYPE)
                .expireTime(expireTime)
                .build();
    }

    @Override
    public UserInfoVO getCurrentUserInfo(Long userId) {
        // 1. 查询用户
        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }

        // 2. 查询角色
        List<UserRole> userRoles = userRoleMapper.selectByUserId(userId);
        List<String> roles = userRoles.stream()
                .map(UserRole::getRoleCode)
                .collect(Collectors.toList());

        // 3. 组装 VO
        return UserInfoVO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .nickname(user.getNickname())
                .avatar(user.getAvatar())
                .email(user.getEmail())
                .phone(user.getPhone())
                .vipStatus(user.getVipStatus())
                .roles(roles)
                .build();
    }

    @Override
    public void logout(String token) {
        Long userId = com.example.context.UserContext.getUserId();
        tokenService.addToBlacklist(token);
        log.info("用户退出登录, id:{}, token已加入黑名单", userId);
    }
}
