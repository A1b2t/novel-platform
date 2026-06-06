package com.example.novelplatformserver.service;

import com.example.dto.category.CategoryCreateDTO;
import com.example.dto.category.CategoryUpdateDTO;
import com.example.vo.category.CategoryVO;

import java.util.List;

/**
 * 分类服务接口
 */
public interface CategoryService {

    /**
     * 创建分类
     */
    Long createCategory(CategoryCreateDTO dto);

    /**
     * 修改分类
     */
    void updateCategory(Long categoryId, CategoryUpdateDTO dto);

    /**
     * 删除分类（逻辑删除）
     */
    void deleteCategory(Long categoryId);

    /**
     * 获取所有启用的分类列表
     */
    List<CategoryVO> getActiveCategories();

    /**
     * 获取所有分类（含禁用）
     */
    List<CategoryVO> getAllCategories();
}
