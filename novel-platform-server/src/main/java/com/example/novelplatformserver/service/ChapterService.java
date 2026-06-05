package com.example.novelplatformserver.service;

import com.example.dto.chapter.ChapterCreateDTO;
import com.example.dto.chapter.ChapterUpdateDTO;
import com.example.vo.chapter.ChapterDetailVO;
import com.example.vo.chapter.ChapterListVO;
import com.example.vo.chapter.ChapterReadVO;

import java.util.List;

/**
 * 章节服务接口
 */
public interface ChapterService {

    /**
     * 创建章节
     */
    Long createChapter(ChapterCreateDTO dto, Long authorId);

    /**
     * 修改章节
     */
    void updateChapter(Long chapterId, ChapterUpdateDTO dto, Long authorId);

    /**
     * 删除章节
     */
    void deleteChapter(Long chapterId, Long authorId);

    /**
     * 发布章节（草稿 → 已发布）
     */
    void publishChapter(Long chapterId, Long authorId);

    /**
     * 下架章节
     */
    void offShelfChapter(Long chapterId, Long authorId);

    /**
     * 获取章节详情（作者端，含内容）
     */
    ChapterDetailVO getChapterDetail(Long chapterId, Long authorId);

    /**
     * 获取已发布的章节列表（读者端）
     */
    List<ChapterListVO> getPublishedChapters(Long novelId);

    /**
     * 获取全部章节列表（作者端，含草稿）
     */
    List<ChapterListVO> getAllChapters(Long novelId, Long authorId);

    /**
     * 阅读章节（读者端，含上下章导航）
     */
    ChapterReadVO readChapter(Long chapterId, Long userId);
}
