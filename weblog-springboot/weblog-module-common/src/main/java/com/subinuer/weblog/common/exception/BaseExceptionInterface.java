package com.subinuer.weblog.common.exception;

// 基础异常接口
public interface BaseExceptionInterface {
    // 用于获取异常码
    String getErrorCode();

    // 用于获取异常信息
    String getErrorMessage();
}
