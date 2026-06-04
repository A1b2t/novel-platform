package com.example.context;

import org.springframework.core.NamedThreadLocal;

/**
 * 当前登录用户上下文
 * 在请求处理期间保存用户 ID，供后续业务使用
 */
public final class UserContext {

    private UserContext() {
    }

    /**
     * 保存当前用户 ID
     */
    private static final ThreadLocal<Long> USER_ID_HOLDER =
            new NamedThreadLocal<>("current-user-id");

    /**
     * 设置当前用户 ID
     */
    public static void setUserId(Long userId) {
        USER_ID_HOLDER.set(userId);
    }

    /**
     * 获取当前用户 ID
     */
    public static Long getUserId() {
        return USER_ID_HOLDER.get();
    }

    /**
     * 是否存在登录用户
     */
    public static boolean hasUser() {
        return USER_ID_HOLDER.get() != null;
    }

    /**
     * 清除当前用户（请求结束必须调用）
     */
    public static void clear() {
        USER_ID_HOLDER.remove();
    }
}
