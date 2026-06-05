package com.example.novelplatformserver.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.Chapter;
import com.example.vo.chapter.ChapterBrief;
import com.example.vo.chapter.ChapterListVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 章节 Mapper
 */
@Mapper
public interface ChapterMapper extends BaseMapper<Chapter> {

    /**
     * 查询小说最大章节序号
     */
    @Select("SELECT COALESCE(MAX(chapter_no), 0) FROM chapter WHERE novel_id = #{novelId} AND deleted = 0")
    Integer selectMaxChapterNo(@Param("novelId") Long novelId);

    /**
     * 查询小说已发布的章节列表（按序号升序）
     */
    @Select("SELECT id, chapter_no, chapter_title, word_count, chapter_type, publish_status, update_time " +
            "FROM chapter " +
            "WHERE novel_id = #{novelId} AND deleted = 0 AND publish_status = 1 " +
            "ORDER BY chapter_no ASC")
    List<ChapterListVO> selectPublishedList(@Param("novelId") Long novelId);

    /**
     * 查询作者的全部章节列表（含草稿，按序号升序）
     */
    @Select("SELECT id, chapter_no, chapter_title, word_count, chapter_type, publish_status, update_time " +
            "FROM chapter " +
            "WHERE novel_id = #{novelId} AND deleted = 0 " +
            "ORDER BY chapter_no ASC")
    List<ChapterListVO> selectAllList(@Param("novelId") Long novelId);

    /**
     * 查询上一章 ID 和标题
     */
    @Select("SELECT id, chapter_title FROM chapter " +
            "WHERE novel_id = #{novelId} AND deleted = 0 AND publish_status = 1 " +
            "AND chapter_no < #{chapterNo} " +
            "ORDER BY chapter_no DESC LIMIT 1")
    ChapterBrief selectPrevChapter(@Param("novelId") Long novelId, @Param("chapterNo") Integer chapterNo);

    /**
     * 查询下一章 ID 和标题
     */
    @Select("SELECT id, chapter_title FROM chapter " +
            "WHERE novel_id = #{novelId} AND deleted = 0 AND publish_status = 1 " +
            "AND chapter_no > #{chapterNo} " +
            "ORDER BY chapter_no ASC LIMIT 1")
    ChapterBrief selectNextChapter(@Param("novelId") Long novelId, @Param("chapterNo") Integer chapterNo);

    /*
      内部辅助类：章节简要信息
      改：ChapterBrief独立
     */
   /* class ChapterBrief {
        private Long id;
        private String chapterTitle;

        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public String getChapterTitle() { return chapterTitle; }
        public void setChapterTitle(String chapterTitle) { this.chapterTitle = chapterTitle; }
    }*/
}
