package com.hope.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DishCategory {
    private Long id;
    private Long shopId; // 所属店铺ID
    private String categoryName; // 分类名称：主食/饮料/小菜
    private Integer sort; // 排序序号
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Integer isDeleted;
}
