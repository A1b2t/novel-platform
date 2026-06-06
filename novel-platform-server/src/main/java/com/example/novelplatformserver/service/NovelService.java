package com.example.novelplatformserver.service;

import com.example.dto.novel.NovelCreateDTO;
import com.example.vo.novel.NovelDetailVO;
import com.example.vo.novel.NovelListVO;

import java.util.List;

/**
 * 小说服务接口
 */
public interface NovelService {

    /**
     * 创建小说
     *
     * @return 小说ID
     */
    Long createNovel(NovelCreateDTO dto, Long authorId);

    /**
     * 获取小说详情
     */
    NovelDetailVO getNovelDetail(Long novelId);

    /**
     * 获取小说列表
     */
    List<NovelListVO> getNovelList();

    /**
     * 模糊搜索小说
     */
    List<NovelListVO> searchNovel(String keyword);

    /**
     * 按分类查询小说
     */
    List<NovelListVO> getNovelsByCategory(Long categoryId);
}
