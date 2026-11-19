package com.hope.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Shop {
    private Long id;    // 店铺ID
    private Long merchantUserId;    // 所属商家账号ID（sys_user.id）
    private String shopName;    // 店铺名称
    private String shopAvatar;  // 店铺头像
    private BigDecimal startDeliveryPrice;   // 起送价
    private BigDecimal deliveryFee;  // 配送费
    private String shopIntro;    // 店铺简介
    private Integer businessStatus;     // 营业状态：0打烊 1营业中
    private Integer shopStatus;     // 平台审核状态：0禁用 1正常
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Integer is_deleted;
}
