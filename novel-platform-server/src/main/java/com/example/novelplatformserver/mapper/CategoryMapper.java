package com.example.novelplatformserver.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 分类 Mapper
 */
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {

    /**
     * 查询所有未删除的分类
     */
    @Select("SELECT id, category_name, sort, status, create_time, update_time " +
            "FROM category WHERE deleted = 0 ORDER BY sort ASC")
    List<Category> selectActiveList();

    /**
     * 查询所有启用且未删除的分类（前台用）
     */
    @Select("SELECT id, category_name, sort, status, create_time, update_time " +
            "FROM category WHERE deleted = 0 AND status = 1 ORDER BY sort ASC")
    List<Category> selectPublishedList();
}
