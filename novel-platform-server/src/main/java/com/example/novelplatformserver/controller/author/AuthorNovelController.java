package com.example.novelplatformserver.controller.author;

import com.example.context.UserContext;
import com.example.dto.novel.NovelCreateDTO;
import com.example.novelplatformserver.service.NovelService;
import com.example.response.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * 作者端 - 小说管理接口
 */
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/author/novel")
@Tag(name = "作者端小说管理接口")
public class AuthorNovelController {

    private final NovelService novelService;

    @Operation(summary = "创建小说")
    @PostMapping
    public Result<Long> createNovel(@RequestBody @Valid NovelCreateDTO dto) {
        Long authorId = UserContext.getUserId();
        Long novelId = novelService.createNovel(dto, authorId);
        return Result.success(novelId);     //优化：觉得返回novelId好一点，前端可以创建成功立即跳详情页
    }
}
