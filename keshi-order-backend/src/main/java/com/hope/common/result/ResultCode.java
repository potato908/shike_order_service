package com.hope.common.result;

public enum ResultCode {
    // 定义具体的状态码
    SUCCESS(200, "操作成功"),
    FAIL(500, "操作失败"),
    INTERNAL_SERVER_ERROR(500, "注册数据写入失败"),
    UNAUTHORIZED(401, "未登录。Token过期"),
    LOGIN_FAIL(401, "用户名或密码错误"),
    FORBIDDEN(403, "没有权限"),
    PARAM_ERROR(400, "参数错误"),
    SYSTEM_ERROR(500, "服务器内部异常"),
    USERNAME_EXISTS(409, "用户名已存在");


    private final Integer code;
    private final String message;

    // 构造
    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    // get 方法
    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
