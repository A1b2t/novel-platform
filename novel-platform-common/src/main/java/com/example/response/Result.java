package com.example.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 统一响应结果
 *
 * @param <T> 数据类型
 */
@Data
@Schema(description = "统一响应结果")
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 状态码
     */
    @Schema(description = "状态码")
    private int code;

    /**
     * 提示信息
     */
    @Schema(description = "提示信息")
    private String msg;

    /**
     * 返回数据
     */
    @Schema(description = "返回数据")
    private T data;

    private Result() {
    }

    private Result(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    /**
     * 成功（无返回数据）
     */
    public static <T> Result<T> success() {
        return new Result<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMsg(), null);
    }

    /**
     * 成功（有返回数据）
     */
    public static <T> Result<T> success(T data) {
        return new Result<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMsg(), data);
    }

    /**
     * 成功（自定义提示）
     */
    public static <T> Result<T> success(String msg, T data) {
        return new Result<>(ResultCode.SUCCESS.getCode(), msg, data);
    }

    /**
     * 失败
     */
    public static <T> Result<T> error() {
        return new Result<>(ResultCode.FAIL.getCode(), ResultCode.FAIL.getMsg(), null);
    }

    /**
     * 失败（自定义信息）
     */
    public static <T> Result<T> error(String msg) {
        return new Result<>(ResultCode.FAIL.getCode(), msg, null);
    }

    /**
     * 失败（自定义状态码和信息）
     */
    public static <T> Result<T> error(int code, String msg) {
        return new Result<>(code, msg, null);
    }

    /**
     * 根据 ResultCode 返回
     */
    public static <T> Result<T> of(ResultCode resultCode) {
        return new Result<>(resultCode.getCode(), resultCode.getMsg(), null);
    }

    /**
     * 根据 ResultCode 返回（带数据）
     */
    public static <T> Result<T> of(ResultCode resultCode, T data) {
        return new Result<>(resultCode.getCode(), resultCode.getMsg(), data);
    }

    /**
     * 判断是否成功
     */
    public boolean isSuccess() {
        return this.code == ResultCode.SUCCESS.getCode();
    }
}
