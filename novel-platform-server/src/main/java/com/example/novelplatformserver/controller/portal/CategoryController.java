package com.example.novelplatformserver.controller.portal;

import com.example.novelplatformserver.service.CategoryService;
import com.example.response.Result;
import com.example.vo.category.CategoryVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 前台 - 分类接口
 */
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/portal/category")
@Tag(name = "前台分类接口")
public class CategoryController {

    private final CategoryService categoryService;

    @Operation(summary = "获取启用的分类列表")
    @GetMapping
    public Result<List<CategoryVO>> getActiveCategories() {
        return Result.success(categoryService.getActiveCategories());
    }
}
