package com.hope.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
public class RegisterDTO {
    @NotBlank
    private String username;

    @NotBlank
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z0-9]{6,12}$",
            message = "密码必须包含大小写字母和数字，长度6-12位")
    private String password;

    @NotBlank
    private String confirmPwd;

    private String nickname;
    private String phone;

    @NotBlank
    private String roleCode;
}
