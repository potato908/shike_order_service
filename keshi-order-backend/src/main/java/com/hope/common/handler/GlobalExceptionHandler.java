package com.hope.common.handler;

import com.hope.common.exception.BusinessException;
import com.hope.common.result.Result;
import com.hope.common.result.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.access.AccessDeniedException;
import java.util.Objects;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({BusinessException.class})
    public Result<?> handleBusinessException(BusinessException be) {
        return Result.fail(be.getCode(), be.getMessage());
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public Result<?> handleValidException(MethodArgumentNotValidException mnve) {
        String message = Objects.requireNonNull(mnve.getBindingResult().getFieldError()).getDefaultMessage();
        log.info("参数校验失败：{}", message);
        return Result.fail(400, message);
    }

    // 未登录、token无效：401
    @ExceptionHandler(AuthenticationException.class)
    public Result<?> authException(AuthenticationException e) {
        log.info("认证失败：{}", e.getMessage());
        return Result.fail(ResultCode.UNAUTHORIZED.getCode(), "未登录或Token无效");
    }

    // 权限不足：403
    @ExceptionHandler(AccessDeniedException.class)
    public Result<?> accessDeniedException(AccessDeniedException e) {
        log.info("权限不足：{}", e.getMessage());
        return Result.fail(ResultCode.FORBIDDEN.getCode(), "权限不足");
    }

    // 兜底异常处理器
    @ExceptionHandler({Exception.class})
    public Result<?> handleAllException(Exception e) {
        log.error("系统未知异常：", e);
        return Result.fail(ResultCode.SYSTEM_ERROR.getCode(), ResultCode.SYSTEM_ERROR.getMessage());
    }
}
