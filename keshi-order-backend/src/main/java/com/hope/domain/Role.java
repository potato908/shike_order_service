package com.hope.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    private Long id;        // 角色ID
    private String roleCode;    // 角色编码：USER/MERCHANT/ADMIN
    private String roleName;        // 角色名称：普通用户/商家/管理员
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
