package com.hope.mapper;

import com.hope.domain.Role;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RoleMapper {

    /**
     * 通过角色编码查询角色信息
     * @param roleCode
     * @return Role
     */
    Long selectRoleIdByCode(String roleCode);
}
