package com.example.novelplatformserver.service.serviceImpl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.constant.NovelStatusConstant;
import com.example.constant.RedisKeyConstant;
import com.example.dto.chapter.ChapterCreateDTO;
import com.example.dto.chapter.ChapterUpdateDTO;
import com.example.entity.*;
import com.example.enums.ChapterPublishStatusEnum;
import com.example.exception.BusinessException;
import com.example.novelplatformserver.mapper.*;
import com.example.novelplatformserver.service.ChapterService;
import com.example.vo.chapter.ChapterBrief;
import com.example.vo.chapter.ChapterDetailVO;
import com.example.vo.chapter.ChapterListVO;
import com.example.vo.chapter.ChapterReadVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 章节服务实现
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ChapterServiceImpl implements ChapterService {

    private final ChapterMapper chapterMapper;
    private final ChapterContentMapper chapterContentMapper;
    private final NovelMapper novelMapper;
    private final StringRedisTemplate redisTemplate;

    /** 章节列表缓存过期时间：5分钟 */
    private static final long CACHE_EXPIRE_MINUTES = 5;

    /*创建章节*/
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createChapter(ChapterCreateDTO dto, Long authorId) {
        // 校验小说是否存在且属于该作者
        Novel novel = novelMapper.selectById(dto.getNovelId());
        if (novel == null || novel.getDeleted() == 1) {
            throw new BusinessException("小说不存在");
        }
        if (!novel.getAuthorId().equals(authorId)) {
            throw new BusinessException("无权操作该小说");
        }
        // 已完结的小说不能新增章节
        if (NovelStatusConstant.FINISHED == novel.getStatus()) {
            throw new BusinessException("小说已完结，无法新增章节");
        }

        // 自动生成章节序号
        Integer maxChapterNo = chapterMapper.selectMaxChapterNo(dto.getNovelId());
        int chapterNo = (maxChapterNo == null ? 0 : maxChapterNo) + 1;

        // 计算字数
        int wordCount = countWords(dto.getContent());

        // 创建章节
        Chapter chapter = new Chapter();
        chapter.setNovelId(dto.getNovelId());
        chapter.setChapterNo(chapterNo);
        chapter.setChapterTitle(dto.getChapterTitle());
        chapter.setWordCount(wordCount);
        chapter.setChapterType(dto.getChapterType());
        // 有内容则直接发布，否则为草稿
        chapter.setPublishStatus(dto.getContent() != null && !dto.getContent().isBlank()
                ? ChapterPublishStatusEnum.PUBLISHED.getCode()
                : ChapterPublishStatusEnum.DRAFT.getCode());
        chapterMapper.insert(chapter);

        // 保存章节内容
        if (dto.getContent() != null && !dto.getContent().isBlank()) {
            ChapterContent chapterContent = new ChapterContent();
            chapterContent.setChapterId(chapter.getId());
            chapterContent.setContent(dto.getContent());
            chapterContentMapper.insert(chapterContent);
        }

        // 更新小说字数、章节数
        novel.setWordCount(novel.getWordCount() + wordCount);   //原本加现在
        novel.setChapterCount(novel.getChapterCount() + 1);
        novelMapper.updateById(novel);

        log.info("创建章节成功, id:{}, novelId:{}, chapterNo:{}", chapter.getId(), dto.getNovelId(), chapterNo);

        // 清除章节列表缓存
        deleteChapterListCache(dto.getNovelId());

        return chapter.getId();
    }


    /*修改章节*/
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateChapter(Long chapterId, ChapterUpdateDTO dto, Long authorId) {
        // 校验章节归属
        Chapter chapter = chapterMapper.selectById(chapterId);
        if (chapter == null || chapter.getDeleted() == 1) {
            throw new BusinessException("章节不存在");
        }
        validateChapterOwner(chapter, authorId);

        // 计算新旧字数差，用于更新小说总字数
        int oldWordCount = chapter.getWordCount();
        int newWordCount = countWords(dto.getContent());

        // 更新章节信息
        chapter.setChapterTitle(dto.getChapterTitle());
        chapter.setChapterType(dto.getChapterType());
        chapter.setWordCount(newWordCount);
        chapterMapper.updateById(chapter);

        // 更新或插入章节内容
        ChapterContent chapterContent = chapterContentMapper.selectByChapterId(chapterId);
        if (chapterContent != null) {
            chapterContent.setContent(dto.getContent());
            chapterContentMapper.updateById(chapterContent);
        } else {
            chapterContent = new ChapterContent();
            chapterContent.setChapterId(chapterId);
            chapterContent.setContent(dto.getContent());
            chapterContentMapper.insert(chapterContent);
        }

        // 更新小说总字数
        Novel novel = novelMapper.selectById(chapter.getNovelId());
        if (novel != null) {
            novel.setWordCount(novel.getWordCount() - oldWordCount + newWordCount);
            novelMapper.updateById(novel);
        }

        log.info("修改章节成功, id:{}", chapterId);

        // 清除章节列表缓存
        deleteChapterListCache(chapter.getNovelId());
    }

    /*删除章节*/
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteChapter(Long chapterId, Long authorId) {
        Chapter chapter = chapterMapper.selectById(chapterId);
        if (chapter == null || chapter.getDeleted() == 1) {
            throw new BusinessException("章节不存在");
        }
        validateChapterOwner(chapter, authorId);

        // “逻辑”删除章节
        chapter.setDeleted(1);
        chapterMapper.updateById(chapter);

        // 更新小说章节数、字数
        Novel novel = novelMapper.selectById(chapter.getNovelId());
        if (novel != null) {
            novel.setChapterCount(Math.max(0, novel.getChapterCount() - 1));
            novel.setWordCount(Math.max(0, novel.getWordCount() - chapter.getWordCount()));
            novelMapper.updateById(novel);
        }

        log.info("删除章节成功, id:{}", chapterId);

        // 清除章节列表缓存
        deleteChapterListCache(chapter.getNovelId());
    }

    /*发布章节*/
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void publishChapter(Long chapterId, Long authorId) {
        Chapter chapter = chapterMapper.selectById(chapterId);
        if (chapter == null || chapter.getDeleted() == 1) {
            throw new BusinessException("章节不存在");
        }
        validateChapterOwner(chapter, authorId);

        // 检查是否有内容
        ChapterContent content = chapterContentMapper.selectByChapterId(chapterId);
        if (content == null || content.getContent() == null || content.getContent().isBlank()) {
            throw new BusinessException("章节内容为空，无法发布");
        }

        if (ChapterPublishStatusEnum.PUBLISHED.getCode() == chapter.getPublishStatus()) {
            throw new BusinessException("章节已发布，无需重复操作");
        }

        chapter.setPublishStatus(ChapterPublishStatusEnum.PUBLISHED.getCode());
        chapterMapper.updateById(chapter);

        log.info("发布章节成功, id:{}", chapterId);

        // 清除章节列表缓存
        deleteChapterListCache(chapter.getNovelId());
    }

    /*下架章节*/
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void offShelfChapter(Long chapterId, Long authorId) {
        Chapter chapter = chapterMapper.selectById(chapterId);
        if (chapter == null || chapter.getDeleted() == 1) {
            throw new BusinessException("章节不存在");
        }
        validateChapterOwner(chapter, authorId);

        chapter.setPublishStatus(ChapterPublishStatusEnum.OFF_SHELF.getCode());
        chapterMapper.updateById(chapter);

        log.info("下架章节成功, id:{}", chapterId);

        // 清除章节列表缓存
        deleteChapterListCache(chapter.getNovelId());
    }

    /*获取章节详情*/
    @Override
    public ChapterDetailVO getChapterDetail(Long chapterId, Long authorId) {
        Chapter chapter = chapterMapper.selectById(chapterId);
        if (chapter == null || chapter.getDeleted() == 1) {
            throw new BusinessException("章节不存在");
        }
        validateChapterOwner(chapter, authorId);

        ChapterDetailVO vo = new ChapterDetailVO();
        BeanUtils.copyProperties(chapter, vo);

        // 查询章节内容
        ChapterContent content = chapterContentMapper.selectByChapterId(chapterId);
        vo.setContent(content != null ? content.getContent() : null);

        return vo;
    }


    /*获取全部章节列表*/
    @Override
    public List<ChapterListVO> getAllChapters(Long novelId, Long authorId) {
        // 校验小说归属
        Novel novel = novelMapper.selectById(novelId);
        if (novel == null || novel.getDeleted() == 1) {
            throw new BusinessException("小说不存在");
        }
        if (!novel.getAuthorId().equals(authorId)) {
            throw new BusinessException("无权操作该小说");
        }
        return chapterMapper.selectAllList(novelId);
    }

    /*获取已发布章节列表（Redis缓存）*/
    @Override
    public List<ChapterListVO> getPublishedChapters(Long novelId) {
        String cacheKey = RedisKeyConstant.CHAPTER_PUBLISHED_LIST + novelId;

        // 1. 先查缓存
        String cached = redisTemplate.opsForValue().get(cacheKey);
        if (cached != null) {
            log.debug("章节列表命中缓存, novelId:{}", novelId);
            return JSON.parseObject(cached, new TypeReference<List<ChapterListVO>>() {});
        }

        // 2. 缓存未命中，查 DB
        List<ChapterListVO> list = chapterMapper.selectPublishedList(novelId);

        // 3. 写入缓存（5分钟过期）
        redisTemplate.opsForValue().set(cacheKey, JSON.toJSONString(list), CACHE_EXPIRE_MINUTES, TimeUnit.MINUTES);

        return list;
    }


    /*阅读章节*/
    @Override
    public ChapterReadVO readChapter(Long chapterId, Long userId) {
        // 查询章节
        Chapter chapter = chapterMapper.selectById(chapterId);
        if (chapter == null || chapter.getDeleted() == 1) {
            throw new BusinessException("章节不存在");
        }
        // 未发布章节不允许阅读
        if (ChapterPublishStatusEnum.PUBLISHED.getCode() != chapter.getPublishStatus()) {
            throw new BusinessException("章节未发布");
        }

        // 查询小说信息
        Novel novel = novelMapper.selectById(chapter.getNovelId());
        if (novel == null || novel.getDeleted() == 1) {
            throw new BusinessException("小说不存在");
        }

        // 查询章节内容
        ChapterContent content = chapterContentMapper.selectByChapterId(chapterId);

        // 查询上下章
        ChapterBrief prevChapter = chapterMapper.selectPrevChapter(chapter.getNovelId(), chapter.getChapterNo());
        ChapterBrief nextChapter = chapterMapper.selectNextChapter(chapter.getNovelId(), chapter.getChapterNo());

        // 组装 VO
        ChapterReadVO vo = ChapterReadVO.builder()
                .id(chapter.getId())
                .novelId(chapter.getNovelId())
                .novelName(novel.getNovelName())
                .chapterNo(chapter.getChapterNo())
                .chapterTitle(chapter.getChapterTitle())
                .content(content != null ? content.getContent() : null)
                .chapterType(chapter.getChapterType())
                .isVip(novel.getIsVip())
                .prevChapterId(prevChapter != null ? prevChapter.getId() : null)
                .prevChapterTitle(prevChapter != null ? prevChapter.getChapterTitle() : null)
                .nextChapterId(nextChapter != null ? nextChapter.getId() : null)
                .nextChapterTitle(nextChapter != null ? nextChapter.getChapterTitle() : null)
                .build();

        // 更新阅读量（异步或直接更新）
        updateReadCount(chapter.getNovelId());

        return vo;
    }




    //内部私有

    /**
     * 校验章节是否属于该作者
     */
    private void validateChapterOwner(Chapter chapter, Long authorId) {
        Novel novel = novelMapper.selectById(chapter.getNovelId());
        if (novel == null || !novel.getAuthorId().equals(authorId)) {
            throw new BusinessException("无权操作该章节");
        }
    }

    /**
     * 计算文本字数（去除空白字符后的字符数）
     */
    private int countWords(String content) {
        if (content == null || content.isBlank()) {
            return 0;
        }
        return content.replaceAll("\\s+", "").length();
    }

    /**
     * 原子自增阅读量（避免并发问题）
     */
    private void updateReadCount(Long novelId) {
        try {
            int affected = novelMapper.incrementReadCount(novelId);
            if (affected == 0) {
                log.warn("阅读量自增失败，小说可能不存在, novelId:{}", novelId);
            }
        } catch (Exception e) {
            log.warn("更新阅读量失败, novelId:{}", novelId, e);
        }
    }

    /**
     * 清除已发布章节列表缓存
     */
    private void deleteChapterListCache(Long novelId) {
        try {
            String cacheKey = RedisKeyConstant.CHAPTER_PUBLISHED_LIST + novelId;
            redisTemplate.delete(cacheKey);
            log.debug("章节列表缓存已清除, novelId:{}", novelId);
        } catch (Exception e) {
            log.warn("清除章节列表缓存失败, novelId:{}", novelId, e);
        }
    }
}
