package com.hope.mapper;

import com.hope.domain.User;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;

@Mapper
public interface UserMapper {

    /**
     * 通过用户名查找用户是否存在
     * @param username
     * @return count
     */
    int countByUsername(String username);


    /**
     * 新增用户
     * @param user
     * @return 影响行数
     */
    int insertUser(User user);

    /**
     * 通过用户名获取用户信息
     * @param username
     * @return User
     */
    User getUserByUsername(String username);


    /**
     * 更新最后一次成功登录的时间
     * @param userId
     * @param lastLoginTime
     * @return 影响行数
     */
    int updateLastLoginTime(Long userId , LocalDateTime lastLoginTime);

    /**
     * 根据用户id获取用户
     * @param userId
     * @return User
     */
    User getUserById(Long userId);

    /**
     * 更新当前用户密码
     * @param passwordHash
     */
    void updatePwd(Long userId, String passwordHash);
}
