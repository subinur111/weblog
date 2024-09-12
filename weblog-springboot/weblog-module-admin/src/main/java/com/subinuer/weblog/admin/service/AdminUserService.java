package com.subinuer.weblog.admin.service;

import com.subinuer.weblog.admin.model.vo.UpdateAdminUserPasswordReqVO;
import com.subinuer.weblog.admin.model.vo.user.FindUserInfoReqVO;
import com.subinuer.weblog.common.utils.Response;

/**
 * 统一处理用户相关的业务代码，与前端的请求交互
 */
public interface AdminUserService {

    // 修改密码
    // ??? 这里的 Response 是哪个response
    Response updatePassword(UpdateAdminUserPasswordReqVO updateAdminUserPasswordReqVO);

    // 获取用户信息
    Response getUserInfo(FindUserInfoReqVO findUserInfoReqVO);
}
