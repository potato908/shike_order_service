package com.hope.mapper;

import com.hope.domain.UserRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserRoleMapper {

    /**
     * 新增用户角色关联信息
     * @param userRole
     * @return 影响的行数
     */
    int insertUserRole(UserRole userRole);


    /**
     * 根据用户id查询角色编码
     * @param userId
     * @return
     */
    List<String> listRoleCodeByUserId(Long userId);
}
