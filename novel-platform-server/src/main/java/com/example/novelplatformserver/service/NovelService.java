package com.example.novelplatformserver.service;

import com.example.dto.novel.NovelCreateDTO;
import com.example.vo.novel.NovelDetailVO;

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
}
