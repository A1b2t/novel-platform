package com.example.novelplatformserver.service.serviceImpl;

import com.example.dto.category.CategoryCreateDTO;
import com.example.dto.category.CategoryUpdateDTO;
import com.example.entity.Category;
import com.example.exception.BusinessException;
import com.example.novelplatformserver.mapper.CategoryMapper;
import com.example.novelplatformserver.service.CategoryService;
import com.example.vo.category.CategoryVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 分类服务实现
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryMapper categoryMapper;

    /**
     * 创建分类
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createCategory(CategoryCreateDTO dto) {
        // 校验名称是否重复
        List<Category> exists = categoryMapper.selectActiveList();
        for (Category c : exists) {
            if (c.getCategoryName().equals(dto.getCategoryName())) {
                throw new BusinessException("分类名称已存在");
            }
        }

        Category category = new Category();
        BeanUtils.copyProperties(dto, category);
        categoryMapper.insert(category);

        log.info("创建分类成功, id:{}, name:{}", category.getId(), category.getCategoryName());
        return category.getId();
    }

    /**
     * 修改分类
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateCategory(Long categoryId, CategoryUpdateDTO dto) {
        Category category = categoryMapper.selectById(categoryId);
        if (category == null || category.getDeleted() == 1) {
            throw new BusinessException("分类不存在");
        }

        // 校验名称是否与其他分类重复
        List<Category> all = categoryMapper.selectActiveList();
        for (Category c : all) {
            if (!c.getId().equals(categoryId) && c.getCategoryName().equals(dto.getCategoryName())) {
                throw new BusinessException("分类名称已存在");
            }
        }

        BeanUtils.copyProperties(dto, category);
        categoryMapper.updateById(category);

        log.info("修改分类成功, id:{}", categoryId);
    }

    /**
     * 删除分类（逻辑删除）
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteCategory(Long categoryId) {
        Category category = categoryMapper.selectById(categoryId);
        if (category == null || category.getDeleted() == 1) {
            throw new BusinessException("分类不存在");
        }

        category.setDeleted(1);
        categoryMapper.updateById(category);

        log.info("删除分类成功, id:{}", categoryId);
    }

    /**
     * 获取所有启用的分类列表
     */
    @Override
    public List<CategoryVO> getActiveCategories() {
        List<Category> list = categoryMapper.selectPublishedList();
        List<CategoryVO> result = new ArrayList<>(list.size());
        for (Category c : list) {
            result.add(toVO(c));
        }
        return result;
    }

    /**
     * 获取所有分类（含禁用）
     */
    @Override
    public List<CategoryVO> getAllCategories() {
        List<Category> list = categoryMapper.selectActiveList();
        List<CategoryVO> result = new ArrayList<>(list.size());
        for (Category c : list) {
            result.add(toVO(c));
        }
        return result;
    }

    /**
     * Entity to VO
     */
    private CategoryVO toVO(Category category) {
        CategoryVO vo = new CategoryVO();
        BeanUtils.copyProperties(category, vo);
        return vo;
    }
}
