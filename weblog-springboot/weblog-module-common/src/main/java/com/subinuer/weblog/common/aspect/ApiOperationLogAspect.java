package com.subinuer.weblog.common.aspect;

import com.subinuer.weblog.common.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

// 这是一个切面类

@Aspect                      // 将当前类标识为一个切面
@Component                   // 这个类需要被加载 创建对象后存储在 IoC H中
@Slf4j                      // 自动生成日志
public class ApiOperationLogAspect {

    // pointcut 切入点， 程序中需要注入advice切面位置的集合，指明该切面在什么条件下被触发
    // 以自定义注解 @ApiOperationLog 为切点，凡是添加 @ApiOperationLog 注解的的方法都会执行下面 环绕 中的代码
    @Pointcut("@annotation(com.subinuer.weblog.common.aspect.ApiOperationLog)")
    public void apiOperationLog() {}

    /*
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    // 环绕
    @Around("apiOperationLog()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable{
        try{
            // 请求开始时间（时间戳）
            long startTime = System.currentTimeMillis();

            // MDC slf4j提供的 轻量级的日志跟踪工具
            // traceid 表示跟踪ID
            MDC.put("traceid", UUID.randomUUID().toString());    // 这里没看懂

            // 获取被请求的类 和 方法
            String className = joinPoint.getTarget().getClass().getSimpleName();
            String methodName = joinPoint.getSignature().getName();

            // 获取 请求参数数组
            Object[] args = joinPoint.getArgs();

            // 将获取到的入参 转换为 json 字符串
            String argsJsonStr = Arrays.stream(args).map(toJsonStr()).collect(Collectors.joining());

            // 功能描述信息
            String description = getApiOperationLogDescription(joinPoint);

            // 打印请求相关参数
            // 因为用了 @Slf4j 注解，因此这里可以直接使用 log.info()
            log.info("====== 请求开始: [{}], 入参: {}, 请求类: {}, 请求方法: {} =================================== ",
                    description, argsJsonStr, className, methodName);

            // 执行切点方法
            // proceed()，调用proceed()方法会继续执行被通知的方法
            Object result = joinPoint.proceed();

            // 执行耗时
            long executionTime = System.currentTimeMillis() - startTime;

            // 打印出参相关信息
            log.info("====== 请求结束: [{}], 耗时: {}ms, 出参: {} =================================== ",
                    description, executionTime);

            return result;

        }finally {
            // 在请求结束时， 为了避免污染其他请求， 还需要清除 MDC 中的值
            MDC.clear();
        }
    }

    //
    private String getApiOperationLogDescription(ProceedingJoinPoint joinPoint){
        // 1. 从 ProceedingJoinPoint 获取 MethodSignature
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();

        // 2. 使用 MethodSignature 获取当前被注解的 Method
        Method method = signature.getMethod();

        // 3. 从 Method 中提取 LogExecution 注解
        ApiOperationLog apiOperationLog = method.getAnnotation(ApiOperationLog.class);

        // 4. 从 LogExecution 注解中获取 description 属性
        return apiOperationLog.description();
    }
    private Function<Object, String> toJsonStr() {
        return arg -> JsonUtil.toJsonString(arg);
//        return JsonUtil::toJsonString;
    }
//    private Function<Object, String> toJsonStr = arg -> JsonUtil.toJsonString(arg);

}
