package com.subinuer.weblog.common.exception;

import com.subinuer.weblog.common.enums.ResponseCodeEnum;
import com.subinuer.weblog.common.utils.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@ControllerAdvice          // 将 GlobalExceptionHandler 声明为了全局异常处理类。
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler({ BizException.class })   // 当抓住这个类型的异常得时候用下面定义的方法处理
    @ResponseBody
    public Response<Object> handleBizException(HttpServletRequest request, BizException e) {
        log.warn("{} request fail, errorCode: {}, errorMessage: {}", request.getRequestURI(), e.getErrorCode(), e.getErrorMessage());
        return Response.fail(e);
    }

    @ExceptionHandler({ Exception.class })
    @ResponseBody
    public  Response<Object> handleOtherException(HttpServletRequest request, Exception e){
        log.error("{} request error, ", request.getRequestURI(), e);
        return Response.fail(ResponseCodeEnum.SYSTEM_ERROR);
    }

    /**
     * 捕获参数异常的校验
     */
    @ExceptionHandler({ MethodArgumentNotValidException.class })
    @ResponseBody
    public Response<Object> handleMethodArgumentNotValidException(HttpServletRequest request, MethodArgumentNotValidException e){
        String errorCode = ResponseCodeEnum.PRODUCT_NOT_FOUND.getErrorCode();

        BindingResult bindingResult = e.getBindingResult();

        // 这是可变的字符串类
        StringBuilder sb = new StringBuilder();

        /** 获取校验不通过的字段，并组合错误信息
         * 格式为： email 邮箱格式不正确, 当前值: '123124qq.com';
         * Optional.ofNullable(T t):创建一个 Optional 对象，如果参数 t 为非空，返回 Optional 描述的指定值，否则返回空的 Optional
         * ifPresent：如果值存在则使用该值调用 括号中的方法 , 否则不做任何事情。
         */
        Optional.ofNullable(bindingResult.getFieldErrors()).ifPresent(errors -> {
            errors.forEach(error ->
                    sb.append(error.getField())
                            .append(" ")
                            .append(error.getDefaultMessage())
                            .append(", 当前值: '")
                            .append(error.getRejectedValue())
                            .append("'; ")

            );
        });

        String errorMessage = sb.toString();

        log.warn("{} request error, errorCode: {}, errorMessage: {}", request.getRequestURI(), errorCode, errorMessage);

        return Response.fail(errorMessage, errorCode);

    }

}
