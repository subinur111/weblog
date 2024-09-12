package com.subinuer.weblog.common.aspect;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)      // 这个元注解用于指定注解的保留策略，即注解在何时(运行时)生效。
@Target(ElementType.METHOD)              // 这个元注解用于指定注解的目标元素，即可以在哪些地方(方法)使用这个注解。
@Documented                              // 这个元注解用于指定被注解的元素是否会出现在生成的Java文档中
public @interface ApiOperationLog {     //这是一个自定义注解

    //    @return
    String description() default "";     // 默认值是空
}

