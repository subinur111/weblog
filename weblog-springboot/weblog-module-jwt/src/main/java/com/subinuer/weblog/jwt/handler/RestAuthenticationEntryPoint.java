package com.subinuer.weblog.jwt.handler;

import com.subinuer.weblog.common.enums.ResponseCodeEnum;
import com.subinuer.weblog.common.utils.Response;
import com.subinuer.weblog.jwt.utils.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 处理当用户未登录时，访问受保护的资源
 */

@Slf4j
@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException{
        log.warn("用户未登录访问受保护资源 ", authException);
        // instanceof是Java的一个二元操作符（运算符）,作用是判断其左边对象是否为其右边类的实例，返回boolean类型的数据。
        // 可以用来判断继承中的子类的实例是否为父类的实现。
        if(authException instanceof InsufficientAuthenticationException){
            ResultUtil.fail(response, HttpStatus.UNAUTHORIZED.value(), Response.fail(ResponseCodeEnum.UNAUTHORIZED));
        }
        ResultUtil.fail(response, HttpStatus.UNAUTHORIZED.value(), Response.fail(authException.getMessage()));
    }


}
