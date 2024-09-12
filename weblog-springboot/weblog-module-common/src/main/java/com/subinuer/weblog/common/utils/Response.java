package com.subinuer.weblog.common.utils;

import com.subinuer.weblog.common.exception.BaseExceptionInterface;
import com.subinuer.weblog.common.exception.BizException;
import lombok.Data;

import java.io.Serializable;

/*
 * 我们希望的响应格式
 * 成功时：
 *      "success": true
 *      "data": null
 * 失败时：
 *      "success": false
 *      "errorCode": "500"
 *      "message": "错误信息"
 */

@Data
public class Response<T> implements Serializable {     // 表示该类为序列化的
    private boolean success = true;          //是否成功,,默认值为成功
    private String message;           // 内容
    private String errorCode;           // 错误码
    private T data;                   // 响应数据，泛类
//    Response(){
//        System.out.println("success 方法0 ");
//    }
    // =================================== 成功响应 ===================================
    public static <T> Response<T> success() {
        Response<T> response = new Response<>();
        return response;
    }

    public static <T> Response<T> success(T data) {
        Response<T> response = new Response<>();
        response.setData(data);
        return response;
    }

    // =================================== 失败响应 ===================================
    // 只有success有值
    public static <T> Response<T> fail() {
        Response<T> response = new Response<>();
        response.setSuccess(false);
        return response;
    }

    // success 和 message 有值
    public static <T> Response<T> fail(String message) {
        Response<T> response = new Response<>();
        response.setSuccess(false);
        response.setMessage(message);
        return response;
    }

    // success 和 message errCode 有值
    public static <T> Response<T> fail(String message, String errorCode){
        Response<T> response = new Response<>();
        response.setSuccess(false);
        response.setMessage(message);
        response.setErrorCode(errorCode);
        return response;
    }

    //入参是 BizException 自定义业务异常
    public static <T> Response<T> fail(BizException bizException){
        Response<T> response = new Response<>();
        response.setSuccess(false);
        response.setMessage(bizException.getErrorMessage());
        response.setErrorCode(bizException.getErrorCode());
        return response;
    }

    public static <T> Response<T> fail(BaseExceptionInterface baseExceptionInterface){
        Response<T> response = new Response<>();
        response.setSuccess(false);
        response.setMessage(baseExceptionInterface.getErrorMessage());
        response.setErrorCode(baseExceptionInterface.getErrorCode());
        return response;
    }

}
