package com.hope.service;

import com.hope.dto.LoginDTO;
import com.hope.dto.RegisterDTO;
import com.hope.dto.UpdatePwdDTO;
import com.hope.vo.LoginVO;
import com.hope.vo.UserVO;


public interface AuthService {

    /**
     * 用户注册
     * @param registerDTO
     * @return 注册是否成功
     */
    public void authRegister(RegisterDTO registerDTO);


    /**
     * 用户登录
     * @param loginDTO
     * @return UserVO
     */
    LoginVO authLogin(LoginDTO loginDTO);


    /**
     * 获取当前登录用户的信息
     * @return
     */
    UserVO getCurrentUserInfo();

    /**
     * 修改当前用户密码
     */
    void updatePwd(UpdatePwdDTO updatePwdDTO);

    /**
     * 用户登出
     * @param token
     */
    void authLogout(String token);
}
