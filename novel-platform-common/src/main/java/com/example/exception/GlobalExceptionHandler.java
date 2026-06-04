package com.example.exception;

import com.example.response.Result;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 * 将所有异常统一转为 Result 格式返回
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 业务异常
     */
    @ExceptionHandler(BusinessException.class)
    public Result<Void> handleBusinessException(BusinessException e) {
        log.warn("业务异常: code={}, msg={}", e.getCode(), e.getMessage());
        return Result.error(e.getCode(), e.getMessage());
    }

    /**
     * 参数校验失败（@Valid 校验 DTO 字段）
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<Void> handleValidationException(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getFieldErrors().stream()
                .findFirst()    //只取第一个失败字段
                .map(FieldError::getDefaultMessage) //提取注解里的 message 值
                .orElse("参数校验失败");  //如果没写 message，写默认文案
        log.warn("参数校验失败: {}", message);
        return Result.error(400, message);
    }

    /**
     * 参数校验失败（普通参数校验）
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public Result<Void> handleConstraintViolationException(ConstraintViolationException e) {
        log.warn("参数校验失败: {}", e.getMessage());
        return Result.error(400, e.getMessage());
    }

    /**
     * 数据库唯一约束冲突（高并发兜底）
     * Service检查（提前拦截）+数据库唯一约束（兜底报错转友好提示）
     */
    //目前就这username的约束，就这一个场景，就不判断message值了
    @ExceptionHandler(DuplicateKeyException.class)
    public Result<Void> handleDuplicateKeyException(DuplicateKeyException e) {
        log.warn("数据重复: {}", e.getMessage());
        return Result.error("用户名已存在");
    }

    /**
     * 未知异常（兜底）
     */
    @ExceptionHandler(Exception.class)
    public Result<Void> handleException(Exception e) {
        log.error("服务器内部异常", e);
        return Result.error(500, "服务器异常");
    }
}
