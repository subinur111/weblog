package com.subinuer.weblog.jwt.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.subinuer.weblog.common.domain.dos.UserDO;
import com.subinuer.weblog.common.domain.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

/*
 * 加载用户信息： 从数据源中加载用户的用户名、密码和角色等信息。
 * 创建 UserDetails 对象： 根据加载的用户信息，创建一个 Spring Security 所需的 UserDetails 对象，
 *      包含用户名、密码、角色和权限等。
 */
@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        // 数据库中查询用户信息
        // 我这里默认只有一个用户
        // 争正常的实现 是 从数据库 根据用户名查询
        // UserDO userDO = userMapper.selectById(1);
        // String usernameDB = userDO.getUsername();
        // String passwordDB = userDO.getPassword();

        // eq 方法是 MyBatis-Plus 中用于构建查询条件的基本方法之一，它用于设置单个字段的相等条件。
        QueryWrapper<UserDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        UserDO userDO =  userMapper.selectOne(queryWrapper);

        if (Objects.isNull((userDO))){
            throw new UsernameNotFoundException("用户不存在");
        }

        String usernameDB = userDO.getUsername();
        String passwordDB = userDO.getPassword();

        // authorities 用于指定角色，这里写死为 ADMIN 管理员
        return User.withUsername(usernameDB)
                .password(passwordDB)
                .authorities("ADMIN")
                .build();
    }
}
