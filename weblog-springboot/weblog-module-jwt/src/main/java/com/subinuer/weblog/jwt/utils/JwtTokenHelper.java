package com.subinuer.weblog.jwt.utils;

import io.jsonwebtoken.security.SignatureException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.JwtParser;
import javafx.util.Builder;
import jdk.nashorn.internal.parser.Token;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.stereotype.Component;
import sun.util.resources.LocaleData;

import java.security.Key;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;

/**
 * 封装所有 JWT 相关功能，生成和解析 JWT
 */
@Component
public class JwtTokenHelper implements InitializingBean {

    private long expirationTime;

    // 密钥
    private Key key;
    // 签发人
    @Value("${jwt.issuer}")
    private String issuer;
    // JWT 解析
    private JwtParser jwtParser;

    // token 过期时间（单位：分钟） 24*60
    @Value("${jwt.tokenExpireTime}")
    private long tokenExpireTime;

    /**
     * 解码 配置文件中配置的 Base 64 编码 key 为秘钥 (何时执行该方法？？)
     * @param base64Key
     */
    @Value("${jwt.secret}")
    public void decodeBase64Key(String base64Key) {
        key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(base64Key));
    }

    /**
     * 实现接口 InitializingBean 中的方法,
     * 这个方法是用来初始化 jwtParser
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception{
        // 考虑到不同服务器之间可能存在时钟偏移，
        // setAllowedClockSkewSeconds 用于设置能够容忍的最大的时钟误差
        jwtParser = Jwts.parserBuilder().requireIssuer(issuer)
                .setSigningKey(key).setAllowedClockSkewSeconds(tokenExpireTime)
                .build();
    }

    // 生成 JWT 令牌
    public String generateToken(String username){
        LocalDateTime now = LocalDateTime.now();
        // 生效时常=== 1 hour
        LocalDateTime expireTime = now.plusMinutes(1400);

        return Jwts.builder()
                .setSubject(username)
                .setIssuer(issuer)
                .setIssuedAt(Date.from(now.atZone(ZoneId.systemDefault()).toInstant()))
                .setExpiration(Date.from(expireTime.atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(key)
                .compact();

    }
    // 解析 JWT 令牌
    public Jws<Claims> parseToken(String token){
        try {
            return jwtParser.parseClaimsJws(token);
        }catch (SignatureException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException e) {
            throw new BadCredentialsException("Token 不可用", e);
        } catch (ExpiredJwtException e) {
            throw new CredentialsExpiredException("Token 失效", e);
        }
    }

    /**
     * 校验 token 是否可用
     *     Jws<Claims> parseClaimsJws(String var1) throws ExpiredJwtException,
     *     UnsupportedJwtException, MalformedJwtException, SignatureException,
     *     IllegalArgumentException;
     *     抛出过期jwt异常，不支持jwt异常，畸形jwt异常，签名异常，非法参数异常;
     */
    public void validateToken(String token){
        jwtParser.parseClaimsJws(token);
    }

    /**
     * 根据 token 解析出用户名
     */
    public String getUsernameByToken(String token){
        /* parseClaimsJws() 用于解析JWT（‌JSON Web Token）‌字符串，‌返回JWT解析后的payload部分,这个方法定义在接口 jwtParser 中
         * 这部分解析出来的是 中间部分 playload，可以获取到我们自定义编码进去的内容 （username）
         *
         */
        try {
            Claims claims = jwtParser.parseClaimsJws(token).getBody();
            String username = claims.getSubject();
            System.out.println("username " + username);
            return username;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


    // 验证

    // 生成 base64 的安全密钥，存放在配置文件.yml 中（只使用一次）
    public static String generateBase64Key(){
        // 生成安全密钥
        Key securityKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);

        // Base64 编码
        return Base64.getEncoder().encodeToString(securityKey.getEncoded());
    }

    public static void main(String[] args){
        System.out.println(generateBase64Key());
    }
}
