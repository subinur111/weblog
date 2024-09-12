package com.subinuer.weblog.jwt.filter;

import com.subinuer.weblog.jwt.utils.JwtTokenHelper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Objects;

/**
 * Token 校验过滤器
 * 用于专门校验 Token 令牌
 * 继承自 OncePerRequestFilter ，保证每一个 请求 都只过滤一次
 */
@Slf4j
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    // token 请求头中的 key 值
    @Value("${jwt.tokenHeaderKey}")
    private String tokenHeaderKey;

    // token 请求头中的 value 值前缀
    @Value("${jwt.tokenPrefix}")
    private String tokenPrefix;

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Autowired
    AuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    UserDetailsService userDetailsService;
    /**
     * 实现抽象类中的抽象方法
     * 定义过滤器处理逻辑
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException{
        // 从请求头中获取 key 为 Authorization 的值，判断是否以 Bearer 开头，
        String header = request.getHeader(tokenHeaderKey);
        System.out.println("header: " +  header);
        if(StringUtils.startsWith(header, tokenPrefix)) {
            // 若是，截取出 Token
            String token = header.substring(7);
            log.info("Token {}", token);

            // 对其进行解析，并对可能抛出的异常做出不同的返参
            // 这里为什么不用 token.isEmpty:因为这个方法只能筛选出 长度为0或者字符串是否为nul，如果是一个“\t"或者"  "等 无效字符串，返回的是非空
            // StringUtils.isNotBlank()方法不仅检查字符串是否为非null且长度大于0，‌还确保字符串不包含空白字符（‌如空格、‌制表符、‌换行符等）‌。‌
            if (StringUtils.isNotBlank(token)) {
                try {
                    // 校验是否可用
                    System.out.println("校验是否可用1");
                    jwtTokenHelper.validateToken(token);
                    System.out.println("校验是否可用2");
                } catch (SignatureException | UnsupportedJwtException | MalformedJwtException | IllegalArgumentException e) {
                    // 校验不通过
                    authenticationEntryPoint.commence(request, response, new AuthenticationServiceException("Token 不可用"));
                    return;
                } catch (ExpiredJwtException e) {
                    authenticationEntryPoint.commence(request, response, new AuthenticationServiceException("Token 已失效"));
                    return;
                }

                // 根据 token 解析出用户名
                String username = jwtTokenHelper.getUsernameByToken(token);
                System.out.println("解析token");

                // 获取用户详细信息
                // loadUserByUsername方法通过接收用户名作为参数，‌然后使用这个用户名去数据库查询相应的用户信息。‌这个过程可能涉及到从数据库中检索用户的详细信息，‌包括用户的角色信息，‌然后将这些信息组装成一个UserDetails对象返回
                if (StringUtils.isNotBlank(username) && Objects.isNull(SecurityContextHolder.getContext().getAuthentication())) {
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                    // 将用户信息存入 authentication（身份验证）
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken
                            (userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    //将 authentication 存入 ThreadLocal 中
                    // 将 authentication 存入 ThreadLocal，方便后续获取用户信息
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }

        // 继续执行写一个过滤器
        filterChain.doFilter(request, response);
    }
}
