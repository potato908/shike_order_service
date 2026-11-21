package com.hope.controller;

import com.hope.common.result.Result;
import com.hope.dto.LoginDTO;
import com.hope.dto.RegisterDTO;
import com.hope.dto.UpdatePwdDTO;
import com.hope.service.AuthService;
import com.hope.vo.LoginVO;
import com.hope.vo.UserVO;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

// 登录、注册、退出、改密码、getInfo
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    // 用户注册
    @PostMapping("/register")
    public Result<?> register(@Validated @RequestBody RegisterDTO registerDTO) {
        authService.authRegister(registerDTO);
        return Result.success("注册成功");
    }


    /**
     * 用户登录
     * @param loginDTO 登录账号密码
     * @return 登录信息 + JWT令牌
     */
    @PostMapping("/login")
    public Result<LoginVO> login(@Validated @RequestBody LoginDTO loginDTO) {
        LoginVO loginVO = authService.authLogin(loginDTO);
        return Result.success(loginVO);
    }

    // 获取当前登录用户信息
    @GetMapping("/getInfo")
    public Result<UserVO> currentUser() {
        UserVO userVO = authService.getCurrentUserInfo();
        return Result.success(userVO);
    }

    // 修改用户密码
    @PostMapping("/updatePwd")
    public Result<?> updatePassword(@Validated @RequestBody UpdatePwdDTO updatePwdDTO) {
        authService.updatePwd(updatePwdDTO);
        return Result.success();
    }

    // 用户登出
    @PostMapping("/logout")
    public Result<?> logout(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            authService.authLogout(token);
        }
        return Result.success();
    }

}
