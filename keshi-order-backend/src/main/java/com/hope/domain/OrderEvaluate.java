package com.hope.domain;

import java.time.LocalDateTime;

public class OrderEvaluate {
    private Long id;
    private Long orderId;    // 一个订单只能评价一次
    private Long shopId;     // 店铺ID
    private Long user_id;     // 评价用户
    private Integer starLevel; // 星级：1~5星
    private String evaluateContent;    // 文字评价
    private LocalDateTime createTime;
}
