package com.example.novelplatformserver.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.UserRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserRoleMapper extends BaseMapper<UserRole> {

    /**
     * 根据用户ID查询角色列表
     */
    @Select("SELECT * FROM user_role WHERE user_id = #{userId}")
    List<UserRole> selectByUserId(Long userId);
}
