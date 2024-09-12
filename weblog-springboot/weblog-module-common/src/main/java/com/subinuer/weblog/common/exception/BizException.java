package com.subinuer.weblog.common.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BizException extends RuntimeException{    // BizException 继承自运行时异常 RuntimeException

    // 定义了两个基本字段：
    //异常码；
    private String errorCode;
    // 错误信息，用于提供给调用者；
    private String errorMessage;

    public BizException(BaseExceptionInterface baseExceptionInterface){
        this.errorCode = baseExceptionInterface.getErrorCode();
        this.errorMessage = baseExceptionInterface.getErrorMessage();
    }
}
