package com.subinuer.weblog.jwt.handler;

import com.subinuer.weblog.common.utils.Response;
import com.subinuer.weblog.jwt.model.LoginRspVO;
import com.subinuer.weblog.jwt.utils.JwtTokenHelper;
import com.subinuer.weblog.jwt.utils.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 此类实现了 Spring Security 的 AuthenticationSuccessHandler 接口，
 * 用于处理身份验证成功后的逻辑。
 * 首先，从 authentication 对象中获取用户的 UserDetails 实例，这里是主要是获取用户的用户名，
 * 然后通过用户名生成 Token 令牌，最后返回数据。
 */
@Component
@Slf4j
public class RestAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException{
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String username = userDetails.getUsername();
        // 生成 token
        String token = jwtTokenHelper.generateToken(username);

        // 返回 Token    ???????
        LoginRspVO loginRspVO = LoginRspVO.builder().token(token).build();
        //
        ResultUtil.ok(response, Response.success(loginRspVO));

    }

}
