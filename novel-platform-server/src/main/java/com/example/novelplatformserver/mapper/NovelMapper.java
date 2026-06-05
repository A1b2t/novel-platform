package com.example.novelplatformserver.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.Novel;
import com.example.vo.novel.NovelDetailVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * 小说 Mapper
 */
@Mapper
public interface NovelMapper extends BaseMapper<Novel> {

    /**
     * 查询小说详情（联表查询作者和分类）
     */
    @Select("SELECT n.*, a.pen_name AS author_name, c.category_name " +
            "FROM novel n " +
            "LEFT JOIN author a ON n.author_id = a.id AND a.deleted = 0 " +
            "LEFT JOIN category c ON n.category_id = c.id AND c.deleted = 0 " +
            "WHERE n.id = #{novelId} AND n.deleted = 0")
    NovelDetailVO selectDetailById(@Param("novelId") Long novelId);

    /**
     * 原子自增阅读量（避免并发问题）
     */
    @Update("UPDATE novel SET read_count = read_count + 1 WHERE id = #{id} AND deleted = 0")
    int incrementReadCount(@Param("id") Long id);
}
