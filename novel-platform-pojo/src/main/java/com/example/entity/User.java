package com.example.entity;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 用户表
 */
@Data
public class User {
    // 用户ID
    private Long id;
    // 用户名
    private String username;
    // 密码
    private String password;
    // 昵称
    private String nickname;
    // 头像
    private String avatar;
    // 邮箱
    private String email;
    // 手机号
    private String phone;
    // 性别 0未知1男2女
    private Integer gender;
    // 会员状态
    private Integer vipStatus;
    // 状态
    private Integer status;
    // 创建时间
    private LocalDateTime createTime;
    // 更新时间
    private LocalDateTime updateTime;
    // 逻辑删除
    private Integer deleted;
}
