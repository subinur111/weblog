package com.subinuer.weblog.admin.service.impl;

import com.subinuer.weblog.admin.model.vo.UpdateAdminUserPasswordReqVO;
import com.subinuer.weblog.admin.model.vo.user.FindUserInfoReqVO;
import com.subinuer.weblog.admin.service.AdminUserService;
import com.subinuer.weblog.common.domain.mapper.UserMapper;
import com.subinuer.weblog.common.enums.ResponseCodeEnum;
import com.subinuer.weblog.common.utils.Response;
import com.subinuer.weblog.jwt.config.PasswordEncoderConfig;
import com.subinuer.weblog.jwt.utils.JwtTokenHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class AdminUserServiceImpl implements AdminUserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoderConfig passwordEncoderConfig;

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Override
    public Response updatePassword(UpdateAdminUserPasswordReqVO updateAdminUserPasswordReqVO){
        String userName = updateAdminUserPasswordReqVO.getUsername();
        String password = updateAdminUserPasswordReqVO.getPassword();

        // 密码先进行加密
        PasswordEncoder encoder = passwordEncoderConfig.passwordEncoder();
        String passwordEncode = encoder.encode(password);

        if(userMapper.updatePasswordByUsername(userName, passwordEncode) == 1){
            // 更新成功，返回成功的报文
            return Response.success();
        }else {
            // 更新失败，返回失败的报文
            return Response.fail(ResponseCodeEnum.USER_NOT_FOUND);
        }
    }

    // 获取用户信息
    public Response getUserInfo(FindUserInfoReqVO findUserInfoReqVO){
        // 根据token 获取用户信息
        String getUserName =  jwtTokenHelper.getUsernameByToken(findUserInfoReqVO.getToken());
        if(Objects.isNull(getUserName)){
            return Response.fail(ResponseCodeEnum.USER_NOT_FOUND);
        }
        return Response.success(getUserName);
    }

}
