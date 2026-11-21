package com.hope.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

/*
* 用于承载jwt中的信息
* */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SecurityUser {
    private Long userId;
    private String username;
    private List<String> roleCodeList;
}
