package com.subinuer.weblog.common.enums;

import com.subinuer.weblog.common.exception.BaseExceptionInterface;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor            // 是什么
public enum ResponseCodeEnum implements BaseExceptionInterface {
    // 声明成员
    SYSTEM_ERROR("10000", "出错啦，后台小姐姐正在努力修复中..."),
    // 测试用
    PRODUCT_NOT_FOUND("20000", "该产品不存在（测试使用）"),
    // 通用异常状态码
    PARAM_NOT_VALID("10001", "参数错误"),

    //
    LOGIN_FAIL("20000", "登录失败"),

    USERNAME_OR_PWD_ERROR("20001", "用户名或密码错误"),

    USERNAME_NOT_FUND_ERROR("20012", "用户不存在"),

    UNAUTHORIZED("20002", "无权访问，请先登录"),

    USER_NOT_FOUND("20003", "用户不存在"),

    CATEGORY_NAME_EXISTED("2005", "该分类名已存在，请勿重复添加")


    ;

    // 异常码
    private String errorCode;
    // 错误信息
    private String errorMessage;

//    @Override
//    public String getErrorCode() {
//        return null;
//    }
//
//    @Override
//    public String getErrorMessage() {
//        return null;
//    }
}
