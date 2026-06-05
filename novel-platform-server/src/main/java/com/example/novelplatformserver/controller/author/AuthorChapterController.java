package com.example.novelplatformserver.controller.author;

import com.example.context.UserContext;
import com.example.dto.chapter.ChapterCreateDTO;
import com.example.dto.chapter.ChapterUpdateDTO;
import com.example.novelplatformserver.service.ChapterService;
import com.example.response.Result;
import com.example.vo.chapter.ChapterDetailVO;
import com.example.vo.chapter.ChapterListVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 作者端 - 章节管理接口
 */
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/author/chapter")
@Tag(name = "作者端章节管理接口")
public class AuthorChapterController {

    private final ChapterService chapterService;

    @Operation(summary = "创建章节")
    @PostMapping
    public Result<Long> createChapter(@RequestBody @Valid ChapterCreateDTO dto) {
        Long authorId = UserContext.getUserId();
        Long chapterId = chapterService.createChapter(dto, authorId);
        return Result.success(chapterId);
    }

    @Operation(summary = "修改章节")
    @PutMapping("/{chapterId}")
    public Result<Void> updateChapter(@PathVariable Long chapterId,
                                      @RequestBody @Valid ChapterUpdateDTO dto) {
        Long authorId = UserContext.getUserId();
        chapterService.updateChapter(chapterId, dto, authorId);
        return Result.success();
    }

    @Operation(summary = "删除章节")
    @DeleteMapping("/{chapterId}")
    public Result<Void> deleteChapter(@PathVariable Long chapterId) {
        Long authorId = UserContext.getUserId();
        chapterService.deleteChapter(chapterId, authorId);
        return Result.success();
    }

    @Operation(summary = "发布章节（草稿 to 已发布）")
    @PutMapping("/{chapterId}/publish")
    public Result<Void> publishChapter(@PathVariable Long chapterId) {
        Long authorId = UserContext.getUserId();
        chapterService.publishChapter(chapterId, authorId);
        return Result.success();
    }

    @Operation(summary = "下架章节")
    @PutMapping("/{chapterId}/off-shelf")
    public Result<Void> offShelfChapter(@PathVariable Long chapterId) {
        Long authorId = UserContext.getUserId();
        chapterService.offShelfChapter(chapterId, authorId);
        return Result.success();
    }

    @Operation(summary = "获取章节详情（作者端，含内容）")
    @GetMapping("/{chapterId}")
    public Result<ChapterDetailVO> getChapterDetail(@PathVariable Long chapterId) {
        Long authorId = UserContext.getUserId();
        return Result.success(chapterService.getChapterDetail(chapterId, authorId));
    }

    @Operation(summary = "获取全部章节列表（含草稿）")
    @GetMapping("/list/{novelId}")
    public Result<List<ChapterListVO>> getAllChapters(@PathVariable Long novelId) {
        Long authorId = UserContext.getUserId();
        return Result.success(chapterService.getAllChapters(novelId, authorId));
    }
}
