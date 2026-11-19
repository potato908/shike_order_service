package com.hope.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingCart {
    private Long id;
    private Long userId; // 用户ID
    private Long shopId; // 店铺ID
    private Long dishId;     // 菜品ID
    private Integer dishNum; // 选购数量
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
