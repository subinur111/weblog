package com.subinuer.weblog.admin.controller;

import com.subinuer.weblog.admin.model.vo.UpdateAdminUserPasswordReqVO;
import com.subinuer.weblog.admin.model.vo.user.FindUserInfoReqVO;
import com.subinuer.weblog.admin.service.AdminUserService;
import com.subinuer.weblog.common.utils.Response;
import com.subinuer.weblog.jwt.utils.JwtTokenHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@Api(tags = "Admin用户模块")
@RequestMapping("/admin")          // 下面的请求路径前都会加上 “/admin”
public class AdminUserController {
    @Autowired
    private AdminUserService adminUserService;

    /**
     * 处理 修改密码的请求
     */
    @RequestMapping(path = "/password/update", method = RequestMethod.POST)
    @ApiOperation(value = "用户密码修改接口")
    public Response updatePasswordReq(@RequestBody @Validated UpdateAdminUserPasswordReqVO updateAdminUserPasswordReqVO){
        // 监听到一个这个请求后，将其分发给 service 层
        log.info("修改密码请求");
        return adminUserService.updatePassword(updateAdminUserPasswordReqVO);
    }

    /**
     * 获取用户信息请求
     * @param findUserInfoReqVO
     * @return
     */
    @RequestMapping(path = "/userInfo", method = RequestMethod.POST)
    public Response findUserInfoReq(@RequestBody @Validated FindUserInfoReqVO findUserInfoReqVO){
        // 从前端获取到请求后，传递给 service 层
        log.info("获取用户信息");
        return adminUserService.getUserInfo(findUserInfoReqVO);
    }
}
