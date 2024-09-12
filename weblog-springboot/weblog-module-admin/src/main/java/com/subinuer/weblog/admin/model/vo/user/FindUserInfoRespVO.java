package com.subinuer.weblog.admin.model.vo.user;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class FindUserInfoRespVO {

    @NotBlank(message = "用户名不能为空")
    private String username;
}
