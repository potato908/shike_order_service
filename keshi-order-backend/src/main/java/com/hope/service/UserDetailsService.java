package com.hope.service;

import org.springframework.security.core.userdetails.UserDetails;

/*
* 职责：Jwt 过滤器解析 token 拿到用户名后，加载用户、封装权限，给 Security 上下文使用
* */
public interface UserDetailsService {

    UserDetails loadUserByUsername(String username);
}
