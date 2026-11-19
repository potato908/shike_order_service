package com.hope.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Order {
    private Long id;    // 订单主键
    private String orderNo;     // 订单编号（唯一字符串）
    private Long userId;    // 下单用户ID
    private Long shopId; // 下单店铺ID
    private Long addressId; // 收货地址ID
    private BigDecimal totalAmount; // 菜品总金额
    private BigDecimal deliveryFee; // 配送费
    private BigDecimal payAmount; // 实付金额（菜品+配送费）
    private Integer payStatus; // 支付状态：0待支付 1已支付
    private Integer orderStatus; // 订单状态：1待接单 2已接单 3配送中 4已完成 5已取消
    private String refuseReason; // 商家拒单原因
    private LocalDateTime createTime; // 下单时间
    private LocalDateTime payTime; // 支付时间
    private LocalDateTime acceptTime; // 商家接单时间
    private LocalDateTime finishTime; // 订单完成时间
}
