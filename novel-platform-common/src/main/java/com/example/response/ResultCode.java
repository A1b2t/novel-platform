package com.example.response;

/**
 * 响应状态码
 */
public enum ResultCode {

    /**
     * 成功
     */
    SUCCESS(200, "操作成功"),

    /**
     * 参数错误
     */
    VALIDATE_FAILED(400, "参数错误"),

    /**
     * 未登录/Token过期
     */
    UNAUTHORIZED(401, "未登录"),

    /**
     * 无权限
     */
    FORBIDDEN(403, "权限不足"),

    /**
     * 资源不存在
     */
    NOT_FOUND(404, "资源不存在"),

    /**
     * 服务器异常
     */
    FAIL(500, "服务器异常");

    /**
     * 状态码
     */
    private final int code;

    /**
     * 提示信息
     */
    private final String msg;

    ResultCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
