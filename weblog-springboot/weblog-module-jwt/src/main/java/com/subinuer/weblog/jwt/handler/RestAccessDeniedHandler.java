package com.subinuer.weblog.jwt.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 主要用于处理当用户登录成功时，访问受保护的资源，但是权限不够的情况
 */
@Slf4j
@Component
public class RestAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException{
        log.warn("登录成功访问收保护的资源，但是权限不够: ", accessDeniedException);
    }


}
