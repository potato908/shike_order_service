package com.hope.service.impl;

import com.hope.domain.User;
import com.hope.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/*
* Spring Security扩展组件
* 人话：暂时没有用
* */
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.getUserByUsername(username);
        if (user == null || user.getIsDeleted() == 1 || user.getStatus() != 1) {
            throw new UsernameNotFoundException("用户不存在或账号禁用");
        }
        // 这里角色我们后续从token里拿，这里基础封装，角色可以先占位
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                List.of()
        );
    }
}
