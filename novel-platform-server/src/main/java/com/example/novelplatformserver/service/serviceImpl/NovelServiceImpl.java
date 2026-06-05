package com.example.novelplatformserver.service.serviceImpl;

import com.example.constant.NovelStatusConstant;
import com.example.dto.novel.NovelCreateDTO;
import com.example.entity.Category;
import com.example.entity.Novel;
import com.example.exception.BusinessException;
import com.example.novelplatformserver.mapper.CategoryMapper;
import com.example.novelplatformserver.mapper.NovelMapper;
import com.example.novelplatformserver.service.NovelService;
import com.example.vo.novel.NovelDetailVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 小说服务实现
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class NovelServiceImpl implements NovelService {

    private final NovelMapper novelMapper;
    private final CategoryMapper categoryMapper;

    /*创建小说*/
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createNovel(NovelCreateDTO dto, Long authorId) {
        // 校验分类是否存在
        Category category = categoryMapper.selectById(dto.getCategoryId());
        if (category == null || category.getDeleted() == 1) {
            throw new BusinessException("分类不存在");
        }

        Novel novel = new Novel();
        BeanUtils.copyProperties(dto, novel);
        novel.setAuthorId(authorId);

        //初始化字段，os:懒得补在entity或者数据库默认值了
        novel.setWordCount(0L);
        novel.setChapterCount(0);
        novel.setStatus(NovelStatusConstant.SERIALIZING);
        novel.setCollectCount(0);
        novel.setCommentCount(0);
        novel.setReadCount(0L);

        novelMapper.insert(novel);
        log.info("创建小说成功, id:{}, name:{},novelName:{}", novel.getId(), novel.getNovelName(),novel.getNovelName());
        return novel.getId();
    }

    /*获取小说详情*/
    @Override
    public NovelDetailVO getNovelDetail(Long novelId) {
        NovelDetailVO vo = novelMapper.selectDetailById(novelId);
        if (vo == null) {
            throw new BusinessException("小说不存在");
        }
        return vo;
    }
}
