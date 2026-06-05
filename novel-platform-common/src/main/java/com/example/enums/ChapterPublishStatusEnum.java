package com.example.enums;

/**
 * 章节发布状态枚举
 */
public enum ChapterPublishStatusEnum {

    /** 草稿 */
    DRAFT(0, "草稿"),

    /** 已发布 */
    PUBLISHED(1, "已发布"),

    /** 下架 */
    OFF_SHELF(2, "下架"),

    /** 审核中 */
    REVIEWING(3, "审核中"),

    /** 违规 */
    VIOLATION(4, "违规"),

    /** 锁定 */
    LOCKED(5, "锁定");

    private final int code;
    private final String desc;

    ChapterPublishStatusEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    /**
     * 根据 code 获取枚举
     */
    public static ChapterPublishStatusEnum fromCode(Integer code) {
        if (code == null) {
            return null;
        }
        for (ChapterPublishStatusEnum e : values()) {
            if (e.code == code) {
                return e;
            }
        }
        return null;
    }
}
