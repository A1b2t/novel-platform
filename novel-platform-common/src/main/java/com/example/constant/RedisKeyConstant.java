package com.example.constant;

/**
 * Redis Key 常量
 */
public interface RedisKeyConstant {

    /** 已发布章节列表缓存 */
    String CHAPTER_PUBLISHED_LIST = "chapter:published:list:";

    /** 章节内容缓存 */
    String CHAPTER_CONTENT = "chapter:content:";
}
