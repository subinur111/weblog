package com.subinuer.weblog.admin.model.vo.user;


import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class FindUserInfoReqVO {
    @NotBlank(message = "token 不能为空")
    String token;
}
