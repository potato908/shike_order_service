package com.hope.common.util;

import com.hope.common.exception.BusinessException;
import com.hope.common.result.ResultCode;
import com.hope.domain.SecurityUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {
    /**
     * 获取当前登录用户
     * @return
     */
    public static SecurityUser getLoginUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return null;
        }
        Object principal = authentication.getPrincipal();
        if (principal instanceof SecurityUser) {
            return (SecurityUser) principal;
        }
        return null;
    }

    public static Long getUserId() {
        SecurityUser loginUser = getLoginUser();
        if (loginUser == null) {
            throw new BusinessException(ResultCode.UNAUTHORIZED.getCode(), "未登录");
        }
        return loginUser.getUserId();
    }
}
