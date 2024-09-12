package com.subinuer.weblog.admin.model.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 修改用户密码接口相关的代码
 */

@Data
public class UpdateAdminUserPasswordReqVO {

    @NotBlank(message = "用户名不能为空")
    private String username;

    @NotBlank(message = "密码不能为空")
    private String password;
}
