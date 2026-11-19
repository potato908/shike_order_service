package com.hope.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Dish {
    private Long id;
    private Long shopId;         // 店铺ID
    private Long categoryId;    // 菜品分类ID
    private String dishName;    // 菜品名称
    private String dishImg;     // 菜品图片
    private BigDecimal price;       // 单价
    private Integer stock; // 库存数量
    private String dishDesc; // 菜品简介
    private Integer is_sale;     // 上下架：0下架 1上架
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Integer isDeleted;
}
