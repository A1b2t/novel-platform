package com.example.novelplatformserver.controller.portal;

import com.example.context.UserContext;
import com.example.novelplatformserver.service.ChapterService;
import com.example.response.Result;
import com.example.vo.chapter.ChapterListVO;
import com.example.vo.chapter.ChapterReadVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 前台 - 章节阅读接口
 */
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/portal/chapter")
@Tag(name = "前台章节阅读接口")
public class ChapterController {

    private final ChapterService chapterService;

    @Operation(summary = "获取已发布章节列表")
    @GetMapping("/list/{novelId}")
    public Result<List<ChapterListVO>> getChapterList(@PathVariable Long novelId) {
        return Result.success(chapterService.getPublishedChapters(novelId));
    }

    @Operation(summary = "阅读章节（含上下章导航）")
    @GetMapping("/read/{chapterId}")
    public Result<ChapterReadVO> readChapter(@PathVariable Long chapterId) {
        Long userId = UserContext.getUserId();
        return Result.success(chapterService.readChapter(chapterId, userId));
    }
}
