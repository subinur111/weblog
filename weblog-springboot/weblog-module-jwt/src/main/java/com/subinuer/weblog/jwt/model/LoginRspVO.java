package com.subinuer.weblog.jwt.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/*
 * 此类是登录的响参类
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginRspVO {

    /**
     * Token 值
     */
    private String token;

}
