package com.example.novelplatformserver.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.Novel;
import com.example.vo.novel.NovelDetailVO;
import com.example.vo.novel.NovelListVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

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

    /**
     * 统计同作者下同名小说数量
     */
    @Select("SELECT COUNT(*) FROM novel WHERE novel_name = #{novelName} AND author_id = #{authorId} AND deleted = 0")
    Long selectCountByNovelNameAndAuthor(@Param("novelName") String novelName, @Param("authorId") Long authorId);

    /**
     * 分页查询已发布小说列表（按更新时间倒序）
     */
    @Select("SELECT n.id, n.novel_name, n.cover_url, n.intro, n.word_count, " +
            "n.chapter_count, n.status, n.is_vip, n.read_count, n.collect_count, " +
            "a.pen_name AS author_name, c.category_name " +
            "FROM novel n " +
            "LEFT JOIN author a ON n.author_id = a.id AND a.deleted = 0 " +
            "LEFT JOIN category c ON n.category_id = c.id AND c.deleted = 0 " +
            "WHERE n.deleted = 0 " +
            "ORDER BY n.update_time DESC")
    List<NovelListVO> selectList();

    /**
     * 按小说名模糊搜索已发布小说（按更新时间倒序）
     */
    @Select("SELECT n.id, n.novel_name, n.cover_url, n.intro, n.word_count, " +
            "n.chapter_count, n.status, n.is_vip, n.read_count, n.collect_count, " +
            "a.pen_name AS author_name, c.category_name " +
            "FROM novel n " +
            "LEFT JOIN author a ON n.author_id = a.id AND a.deleted = 0 " +
            "LEFT JOIN category c ON n.category_id = c.id AND c.deleted = 0 " +
            "WHERE n.deleted = 0 AND n.novel_name LIKE CONCAT('%', #{keyword}, '%') " +
            "ORDER BY n.update_time DESC")
    List<NovelListVO> searchByKeyword(@Param("keyword") String keyword);

    /**
     * 按分类查询已发布小说（按更新时间倒序）
     */
    @Select("SELECT n.id, n.novel_name, n.cover_url, n.intro, n.word_count, " +
            "n.chapter_count, n.status, n.is_vip, n.read_count, n.collect_count, " +
            "a.pen_name AS author_name, c.category_name " +
            "FROM novel n " +
            "LEFT JOIN author a ON n.author_id = a.id AND a.deleted = 0 " +
            "LEFT JOIN category c ON n.category_id = c.id AND c.deleted = 0 " +
            "WHERE n.deleted = 0 AND n.category_id = #{categoryId} " +
            "ORDER BY n.update_time DESC")
    List<NovelListVO> selectByCategoryId(@Param("categoryId") Long categoryId);
}
