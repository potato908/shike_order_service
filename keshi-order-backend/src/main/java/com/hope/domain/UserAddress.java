package com.hope.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAddress {
    private Long id;
    private Long userId;     // 普通用户ID（sys_user.id）
    private String receiverName;    // 收货人
    private String receiverPhone; // 联系电话
    private String addressDetail; // 详细收货地址'
    private Integer isDefault; // 是否默认地址：0否 1是
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Integer isDeleted;
}
