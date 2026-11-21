package com.hope.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
public class UpdatePwdDTO {
    @NotBlank
    private String oldPwd;

    @NotBlank
    private String newPwd;

    @NotBlank
    private String confirmPwd;
}
