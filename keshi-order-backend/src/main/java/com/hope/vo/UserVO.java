package com.hope.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserVO {
    private Long id;     // 用户雪花 ID
    private String username;    // 账号
    private String nickname;    // 昵称
    private String phone;   // 手机号
    private String avatar;      // 头像地址
    private List<String> roleCodeList;  // 当前账号角色编码集合（["USER"] / ["MERCHANT"] / ["ADMIN"]）
}
