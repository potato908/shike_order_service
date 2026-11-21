package com.hope.service.impl;

import com.hope.common.exception.BusinessException;
import com.hope.common.result.ResultCode;
import com.hope.common.util.JwtUtil;
import com.hope.common.util.SecurityUtil;
import com.hope.common.util.SnowflakeUtil;
import com.hope.domain.User;
import com.hope.domain.UserRole;
import com.hope.dto.LoginDTO;
import com.hope.dto.RegisterDTO;
import com.hope.dto.UpdatePwdDTO;
import com.hope.mapper.RoleMapper;
import com.hope.mapper.UserMapper;
import com.hope.mapper.UserRoleMapper;
import com.hope.service.AuthService;
import com.hope.vo.LoginVO;
import com.hope.vo.UserVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserMapper userMapper;
    private final SnowflakeUtil snowflakeUtil;
    private final PasswordEncoder passwordEncoder;
    private final UserRoleMapper userRoleMapper;
    private final RoleMapper roleMapper;
    private final JwtUtil jwtUtil;
/*    private final StringRedisTemplate stringRedisTemplate;
    private static final String TOKEN_BLACK_PREFIX = "token:black:";*/

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void authRegister(RegisterDTO registerDTO) {
        // roleCode 不是 USER、MERCHANT 直接拦截，提示非法角色
        if (!registerDTO.getRoleCode().equals("USER") && !registerDTO.getRoleCode().equals("MERCHANT")) {
            throw new BusinessException(
                    ResultCode.PARAM_ERROR.getCode(),
                    "用户编码输入错误"
            );
        }

        // 密码确认验证
        if (!registerDTO.getConfirmPwd().equals(registerDTO.getPassword())) {
            throw new BusinessException(
                    ResultCode.PARAM_ERROR.getCode(),
                    "确认密码输入错误"
            );
        }

        // 查询用户名是否已存在
        int countOfUser = userMapper.countByUsername(registerDTO.getUsername());
        if (countOfUser == 1) {
            throw new BusinessException(
                    ResultCode.USERNAME_EXISTS.getCode(),
                    ResultCode.USERNAME_EXISTS.getMessage()
            );
        }
        // 生成雪花id
        Long userId = snowflakeUtil.nextId();
        Long userRoleId = snowflakeUtil.nextId();
        // 对密码进行BCrypt 加密
        String passwordHash = passwordEncoder.encode(registerDTO.getPassword());
        // 获取对应角色的id
        Long roleId = roleMapper.selectRoleIdByCode(registerDTO.getRoleCode());
        if (roleId == null) {
            throw new BusinessException(ResultCode.PARAM_ERROR.getCode(), "角色不存在");
        }

        // 设置用户信息
        User user = new User();
        user.setId(userId);
        user.setUsername(registerDTO.getUsername());
        user.setPassword(passwordHash);
        user.setNickname(registerDTO.getNickname());
        user.setPhone(registerDTO.getPhone());
        user.setStatus(1);
        user.setIsDeleted(0);

        // 设置用户角色关联信息
        UserRole userRole = new UserRole();
        userRole.setId(userRoleId);
        userRole.setUserId(userId);
        userRole.setRoleId(roleId);

        // 传入用户信息
        int countOfInsertUser = userMapper.insertUser(user);

        // 在 sys_user_role 插入用户角色关联记录
        int countOfUserRole = userRoleMapper.insertUserRole(userRole);

        if (countOfInsertUser != 1 || countOfUserRole != 1 ) {
            throw new BusinessException(
                    ResultCode.INTERNAL_SERVER_ERROR.getCode(),
                    ResultCode.INTERNAL_SERVER_ERROR.getMessage()
            );
        }
    }

    @Override
    public LoginVO authLogin(LoginDTO loginDTO) {
        // 查询 sys_user，账号不存在 → 登录失败
        // 获取用户信息
        User user = userMapper.getUserByUsername(loginDTO.getUsername());
        if (user == null) {
            throw new BusinessException(
                    ResultCode.PARAM_ERROR.getCode(),
                    "用户不存在"
            );
        }

        // 获取 status
        Integer status = user.getStatus();
        // 校验 status=0（账号禁用）→ 返回账号已被禁用
        if (status == 0) {
            throw new BusinessException(
                    ResultCode.LOGIN_FAIL.getCode(),
                    ResultCode.LOGIN_FAIL.getMessage()
            );
        }

        // BCrypt 比对密码，密码错误 → 登录失败
        boolean match = passwordEncoder.matches(loginDTO.getPassword(), user.getPassword());
        if (!match) {
            throw new BusinessException(
                    ResultCode.LOGIN_FAIL.getCode(),
                    ResultCode.LOGIN_FAIL.getMessage()
            );
        }

        // 查询 sys_user_role 关联的角色集合，封装角色信息
        List<String> listRoleCode = userRoleMapper.listRoleCodeByUserId(user.getId());
        // 更新 sys_user.last_login_time 为当前登录时间
        LocalDateTime now = LocalDateTime.now();
        userMapper.updateLastLoginTime(user.getId(), now);

        // 生成jwt令牌
        // 后期存入redis
        String token = jwtUtil.createToken(user.getId(), user.getUsername(), listRoleCode);

        // 封装VO返回
        LoginVO loginVO = new LoginVO();
        loginVO.setId(user.getId());
        loginVO.setUsername(user.getUsername());
        loginVO.setNickname(user.getNickname());
        loginVO.setPhone(user.getPhone());
        loginVO.setAvatar(user.getAvatar());
        loginVO.setRoleCodeList(listRoleCode);
        loginVO.setToken(token);
        // 返回用户 VO（不返回 password）
        return loginVO;
    }


    @Override
    public UserVO getCurrentUserInfo() {
        Long userId = SecurityUtil.getUserId();

        User user = userMapper.getUserById(userId);
        if (user == null) {
            throw new BusinessException(
                    ResultCode.UNAUTHORIZED.getCode(),
                    "用户不存在或已禁用"
            );
        }

        // 查询角色
        List<String> userRoleList = userRoleMapper.listRoleCodeByUserId(userId);

        // 查询角色
        UserVO userVO = new UserVO();
        userVO.setId(user.getId());
        userVO.setUsername(user.getUsername());
        userVO.setNickname(user.getNickname());
        userVO.setPhone(user.getPhone());
        userVO.setAvatar(user.getAvatar());
        userVO.setRoleCodeList(userRoleList);

        return userVO;
    }

    @Override
    public void updatePwd(UpdatePwdDTO updatePwdDTO) {
        // 确认用户密码输入正确
        String oldPwd = updatePwdDTO.getOldPwd();
        String newPwd = updatePwdDTO.getNewPwd();
        String confirmPwd = updatePwdDTO.getConfirmPwd();

        if (oldPwd.equals(newPwd) || !newPwd.equals(confirmPwd)) {
            throw new BusinessException(
                    ResultCode.PARAM_ERROR.getCode(),
                    ResultCode.PARAM_ERROR.getMessage()
            );
        }

        // 获取当前用户id
        Long userId = SecurityUtil.getUserId();
        User user = userMapper.getUserById(userId);
        if (user == null) {
            throw new BusinessException(
                    ResultCode.UNAUTHORIZED.getCode(),
                    "用户不存在"
            );
        }

        //  校验旧密码是否正确（BCrypt校验，不能直接字符串equals！）
        if (!passwordEncoder.matches(oldPwd, user.getPassword())) {
            throw new BusinessException(
                    ResultCode.PARAM_ERROR.getCode(),
                    "旧密码错误"
            );
        }

        // 对新密码加密
        String passwordHash = passwordEncoder.encode(newPwd);

        // 通过id修改用户密码
        userMapper.updatePwd(userId, passwordHash);
    }

    @Override
    public void authLogout(String token) {
        // 无redis暂时不做处理，由前端删除session里面的jwt
        /*// 放入黑名单，过期时间和jwt过期保持一致，这里简化先固定时长
        stringRedisTemplate
                .opsForValue()
                .set(TOKEN_BLACK_PREFIX + token, "1", 30, TimeUnit.MINUTES);*/
    }
}
