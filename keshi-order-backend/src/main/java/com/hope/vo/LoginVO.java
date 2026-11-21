package com.hope.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginVO {
    private Long id;                    // 用户雪花ID
    private String username;            // 登录账号
    private String nickname;            // 用户昵称
    private String phone;               // 手机号
    private String avatar;              // 头像地址（暂无数据时返回null，预留扩展）
    private List<String> roleCodeList;  // 角色编码集合：["ADMIN"] / ["USER"] / ["MERCHANT"]，支持多角色
    private String token;               // JWT身份令牌，返回前端，后续鉴权携带
}