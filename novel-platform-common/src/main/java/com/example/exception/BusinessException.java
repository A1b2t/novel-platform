package com.example.exception;

/**
 * 业务异常
 * <p>
 * 用于业务逻辑校验失败时抛出，由全局异常处理器统一捕获并返回 Result
 */
public class BusinessException extends RuntimeException {

    private final int code;

    public BusinessException(String message) {
        super(message);
        this.code = 400;
    }

    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
