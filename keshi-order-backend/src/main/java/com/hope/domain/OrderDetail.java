package com.hope.domain;

import java.math.BigDecimal;

public class OrderDetail {
    private Long id;
    private Long orderId; // 订单主表ID
    private Long dishId; // 菜品ID
    private String dishName; // 菜品名称（冗余，防止菜品修改）
    private String dish_img; // 菜品图片
    private BigDecimal dish_price; // 下单时单价
    private Integer dishNum; // 购买数量
    private BigDecimal subAmount; // 小计金额
}
