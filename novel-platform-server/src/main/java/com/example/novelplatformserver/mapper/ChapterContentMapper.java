package com.example.novelplatformserver.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.ChapterContent;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * 章节内容 Mapper
 */
@Mapper
public interface ChapterContentMapper extends BaseMapper<ChapterContent> {

    /**
     * 根据章节ID查询内容
     */
    @Select("SELECT id, chapter_id, content FROM chapter_content WHERE chapter_id = #{chapterId}")
    ChapterContent selectByChapterId(Long chapterId);
}
