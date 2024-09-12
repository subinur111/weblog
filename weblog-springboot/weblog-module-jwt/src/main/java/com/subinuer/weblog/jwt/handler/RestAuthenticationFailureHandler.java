package com.subinuer.weblog.jwt.handler;

import com.subinuer.weblog.common.enums.ResponseCodeEnum;
import com.subinuer.weblog.common.utils.Response;
import com.subinuer.weblog.jwt.exception.UsernamePasswordNullException;
import com.subinuer.weblog.jwt.utils.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class RestAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException{
        log.warn("AuthenticationException: ", exception);
        if (exception instanceof UsernamePasswordNullException) {
            // 用户名或密码为空
            ResultUtil.fail(response, Response.fail(exception.getMessage()));
        } else if (exception instanceof UsernameNotFoundException){
            ResultUtil.fail(response, Response.fail(ResponseCodeEnum.USERNAME_NOT_FUND_ERROR));
        }
        else if(exception instanceof BadCredentialsException) {
            // 用户名或密码错误
            ResultUtil.fail(response, Response.fail(ResponseCodeEnum.USERNAME_OR_PWD_ERROR));
        }

        // 登录失败
        ResultUtil.fail(response, Response.fail(ResponseCodeEnum.LOGIN_FAIL));
    }


}
