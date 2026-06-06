package com.example.novelplatformserver.controller.common;

import com.example.novelplatformserver.service.ChapterService;
import com.example.novelplatformserver.service.NovelService;
import com.example.response.Result;
import com.example.vo.chapter.ChapterListVO;
import com.example.vo.chapter.ChapterReadVO;
import com.example.vo.novel.NovelDetailVO;
import com.example.vo.novel.NovelListVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 公开 - 小说浏览接口（无需登录）
 */
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/public")
@Tag(name = "公开浏览接口")
public class PublicNovelController {

    private final NovelService novelService;
    private final ChapterService chapterService;

    @Operation(summary = "按分类查询小说")
    @GetMapping("/novels/category/{categoryId}")
    public Result<List<NovelListVO>> getNovelsByCategory(@PathVariable Long categoryId) {
        return Result.success(novelService.getNovelsByCategory(categoryId));
    }

    @Operation(summary = "获取小说列表（按更新时间倒序）")
    @GetMapping("/novels")
    public Result<List<NovelListVO>> getNovelList() {
        return Result.success(novelService.getNovelList());
    }


    @Operation(summary = "获取小说详情")
    @GetMapping("/novels/{id}")
    public Result<NovelDetailVO> getNovelDetail(@PathVariable Long id) {
        return Result.success(novelService.getNovelDetail(id));
    }


    @Operation(summary = "搜索小说（按小说名模糊查询）")
    @GetMapping("/novels/search")
    public Result<List<NovelListVO>> searchNovel(@RequestParam String keyword) {
        return Result.success(novelService.searchNovel(keyword));
    }


    @Operation(summary = "获取已发布章节列表")
    @GetMapping("/novels/{id}/chapters")
    public Result<List<ChapterListVO>> getChapterList(@PathVariable Long id) {
        return Result.success(chapterService.getPublishedChapters(id));
    }

    @Operation(summary = "阅读章节")
    @GetMapping("/chapters/{id}")
    public Result<ChapterReadVO> readChapter(@PathVariable Long id) {
        return Result.success(chapterService.readChapter(id, null));
    }


}
