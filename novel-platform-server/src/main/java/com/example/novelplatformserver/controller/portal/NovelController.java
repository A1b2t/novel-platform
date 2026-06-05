package com.example.novelplatformserver.controller.portal;

import com.example.novelplatformserver.service.NovelService;
import com.example.response.Result;
import com.example.vo.novel.NovelDetailVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * 前台 - 小说接口
 */
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/portal/novel")
@Tag(name = "前台小说接口")
public class NovelController {

    private final NovelService novelService;

    @Operation(summary = "获取小说详情")
    @GetMapping("/{id}")
    public Result<NovelDetailVO> getDetail(@PathVariable Long id) {
        return Result.success(novelService.getNovelDetail(id));
    }
}
