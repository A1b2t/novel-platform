package com.example.novelplatformserver.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * 用户 Mapper
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {


    /*可以在service层用，但是复杂，且这个select常用，就还是老老实实自定义
      LambdaQueryWrapper<User> wrapper = Wrappers.lambdaQuery<User>()
                          .eq(User::getUsername, "novel_fan");
      User user = userMapper.selectOne(wrapper);
     */
    /**
     * 根据用户名查询用户
     */
    //@Select("SELECT * FROM user WHERE username = #{username}")，优化：
    @Select("SELECT * FROM user WHERE username = #{username} AND deleted = 0 LIMIT 1")
    User selectByUsername(String username);
}
