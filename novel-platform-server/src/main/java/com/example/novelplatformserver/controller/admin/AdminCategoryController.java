package com.example.novelplatformserver.controller.admin;

import com.example.dto.category.CategoryCreateDTO;
import com.example.dto.category.CategoryUpdateDTO;
import com.example.novelplatformserver.service.CategoryService;
import com.example.response.Result;
import com.example.vo.category.CategoryVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 管理端 - 分类管理接口
 */
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/category")
@Tag(name = "管理端分类管理接口")
public class AdminCategoryController {

    private final CategoryService categoryService;

    @Operation(summary = "创建分类")
    @PostMapping
    public Result<Long> createCategory(@RequestBody @Valid CategoryCreateDTO dto) {
        return Result.success(categoryService.createCategory(dto));
    }

    @Operation(summary = "修改分类")
    @PutMapping("/{categoryId}")
    public Result<Void> updateCategory(@PathVariable Long categoryId,
                                       @RequestBody @Valid CategoryUpdateDTO dto) {
        categoryService.updateCategory(categoryId, dto);
        return Result.success();
    }

    @Operation(summary = "删除分类")
    @DeleteMapping("/{categoryId}")
    public Result<Void> deleteCategory(@PathVariable Long categoryId) {
        categoryService.deleteCategory(categoryId);
        return Result.success();
    }

    @Operation(summary = "获取所有分类（含禁用）")
    @GetMapping
    public Result<List<CategoryVO>> getAllCategories() {
        return Result.success(categoryService.getAllCategories());
    }
}
