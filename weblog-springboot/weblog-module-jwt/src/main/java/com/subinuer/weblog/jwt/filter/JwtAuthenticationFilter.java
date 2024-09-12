package com.subinuer.weblog.jwt.filter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.subinuer.weblog.jwt.exception.UsernamePasswordNullException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义认证过滤器（验证过程）
 * 如果登录验证通过，则生成token AuthenticationSuccessHandler接口
 * 如果登录验证失败，则提示 AuthenticationFailureHandler接口
 */
public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
   /*
    * 指定用户登录的访问地址
    * 调用 父类 的构造函数 （super调用父类构造方法的基本语法是super([参数列表])）
    *     protected AbstractAuthenticationProcessingFilter(RequestMatcher requiresAuthenticationRequestMatcher)
    * 当请求路径匹配 /login 并且请求方法为 POST 时，该过滤器将被触发
    */
    public JwtAuthenticationFilter(){
        super(new AntPathRequestMatcher("/login", "POST"));
    }

    /* 这个是父类中的抽象方法 需实现
     * 实现用户身份验证的具体逻辑
     * 解析了提交的 JSON 数据，并获取了用户名、密码，校验是否为空，
     * 若不为空，则将它们封装到 UsernamePasswordAuthenticationToken 中。
     * 最后，使用 getAuthenticationManager().authenticate() 来触发 Spring Security 的身份验证管理器执行实际的身份验证过程，
     * 然后返回身份验证结果。
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException{
        // 解析用户提交的 JSON 数据，获取用户名 密码 校验是否为空
        // 用户提交的 JSON 数据格式是什么样的：HttpServletRequest类的
        // 如何从中读取body：可以通过 HttpServletRequest类的方法
        // 但是我们想直接解析 收到的 JSON 串
        // 这个类 ObjectMapper是Jackson的核心功能，可以实现Java对象JSON字符串之间转换，Jackson 是一个强大的 JSON 处理库
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(request.getInputStream());
        JsonNode usernameNode = jsonNode.get("username");
        JsonNode passwordNode = jsonNode.get("password");
        String usernameString = usernameNode.textValue();
        String passwordString = passwordNode.textValue();
        // 测试用：
        // System.out.println("usernameNode   "+ usernameNode + "  passwordNode" + passwordNode + "   ");
        // System.out.println("usernameString   " + usernameString + "   passwordString" + passwordString);
        // System.out.println("usernameString.isEmpty()  " + usernameString.isEmpty());
        // System.out.println("usernameString.isNull()  " + usernameString.());
        // 校验是否为空   ???
        if (usernameString.isEmpty() || passwordString.isEmpty())
        {
            throw new UsernamePasswordNullException("账号密码不能为空");
        }

        // 不为空 封装到 UsernamePasswordAuthenticationToken 中

        //     public UsernamePasswordAuthenticationToken(Object principal, Object credentials) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(usernameString, passwordString);


        return getAuthenticationManager().authenticate(usernamePasswordAuthenticationToken);
    }



}
