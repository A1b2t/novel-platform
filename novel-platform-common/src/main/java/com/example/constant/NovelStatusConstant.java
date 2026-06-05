package com.example.constant;

/**
 * 小说状态常量
 */
public interface NovelStatusConstant {

    /** 连载中 */
    int SERIALIZING = 0;

    /** 已完结 */
    int FINISHED = 1;

    /** 草稿 */
    int DRAFT = 2;

    /** 审核中 */
    int REVIEWING = 3;

    /** 下架 */
    int OFF_SHELF = 4;

    /** 冻结 */
    int FROZEN = 5;
}
