package com.subinuer.weblog.admin.config;

import com.subinuer.weblog.jwt.config.JwtAuthenticationSecurityConfig;
import com.subinuer.weblog.jwt.filter.TokenAuthenticationFilter;
import com.subinuer.weblog.jwt.handler.RestAccessDeniedHandler;
import com.subinuer.weblog.jwt.handler.RestAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private JwtAuthenticationSecurityConfig jwtAuthenticationSecurityConfig;
    /**
     * 下面这种写法，会将url 为/admin/**的访问者进行拒绝访问资源，返回403
     * 但是没有用到 我们自定义的针对带有token 的有权限访问的用户进行筛选
     */
    // @Override
    // protected void configure(HttpSecurity http) throws Exception {
    //     http.csrf().disable(). // 禁用 csrf
    //             formLogin().disable() // 禁用表单登录
    //             .apply(jwtAuthenticationSecurityConfig) // 设置用户登录认证相关配置
    //             .and()
    //             .authorizeHttpRequests()
    //             .mvcMatchers("/admin/**").authenticated() // 认证所有以 /admin 为前缀的 URL 资源
    //             .anyRequest().permitAll() // 其他都需要放行，无需认证
    //             .and()
    //             .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); // 前后端分离，无需创建会话
    // }


    /**
     * 下面，我们将自定义的 token 过滤器 添加到我们的 Spring Security 中
     */
    @Autowired
    private RestAuthenticationEntryPoint authEntryPoint;
    @Autowired
    private RestAccessDeniedHandler deniedHandler;
    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.csrf().disable()  // 禁用 csrf
                .formLogin().disable() // 禁用表单登录
                .apply(jwtAuthenticationSecurityConfig)  // 设置用户登录认证相关配置
                .and()
                .authorizeHttpRequests()
                .mvcMatchers("/admin/**").authenticated()  // 认证所有以 /admin 为前缀的 URL 资源
                .anyRequest().permitAll()    // 其他都需要放行，无需认证
                .and()
                .httpBasic().authenticationEntryPoint(authEntryPoint)  // 处理用户未登录访问受保护的资源的情况
                .and()
                .exceptionHandling().accessDeniedHandler(deniedHandler) // 处理登录成功后访问受保护的资源，但是权限不够的情况
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)  // 前后端分离，无需创建会话
                .and()
                .addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)  // 将 Token 校验过滤器添加到用户认证过滤器之前
                ;
    }

    /**
     * token 校验过滤器
     * @return
     */
    @Bean
    public TokenAuthenticationFilter tokenAuthenticationFilter(){
        return new TokenAuthenticationFilter();
    }

}
