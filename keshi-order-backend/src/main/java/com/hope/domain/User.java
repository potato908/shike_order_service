package com.hope.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Long id;            // 用户ID（雪花ID）
    private String username;    // 登录账号
    private String password;    // BCrypt加密密码
    private String nickname;     // 昵称
    private String avatar;       // 头像URL
    private String phone;        // 手机号
    private Integer status;       // 账号状态：0禁用 1正常
    private LocalDateTime lastLoginTime;    // 最后登录时间
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Integer isDeleted;               // 逻辑删除：0未删 1已删
}
