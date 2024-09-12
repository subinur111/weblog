// package com.subinuer.weblog.web.controller;
//
// import com.subinuer.weblog.common.utils.JsonUtil;
// import com.subinuer.weblog.jwt.filter.JwtAuthenticationFilter;
// import com.subinuer.weblog.web.model.User;
// import com.subinuer.weblog.common.aspect.ApiOperationLog;
// import com.subinuer.weblog.common.utils.Response;
// import io.swagger.annotations.Api;
// import io.swagger.annotations.ApiOperation;
// import lombok.extern.slf4j.Slf4j;
// import org.springframework.validation.BindingResult;
// import org.springframework.validation.annotation.Validated;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RestController;
//
// import java.time.LocalDate;
// import java.time.LocalDateTime;
// import java.time.LocalTime;
// import java.util.stream.Collectors;
//
// @RestController
// @Slf4j
// @Api(tags = "首页")
// public class testController {
//
//     /************** 粗糙的参数验证：手动写  *******************/
// //    @PostMapping("/test")
// //    @ApiOperationLog(description = "测试接口")
// //    public User test(@RequestBody User user)
// //    {
// //        if (user.getName() == null || user.getName().trim().isEmpty()) {
// //            System.out.println(ResponseEntity.badRequest().body("姓名不能为空"));
// //        }
// //
// //        if (user.getSex() != 1 ) {
// //            System.out.println(ResponseEntity.badRequest().body("年龄必须在18到100之间"));
// //        }
// //        return user;
// //    }
//
//     /********** JSR 380 参数校验注解 实现参数验证 **************
//     /*  关键部分详解
//      * @Validated: 告诉 Spring 需要对 User 对象执行校验;如果校验出异常，则会触发 全局异常处理类 GlobalExceptionHandler
//      * BindingResult : 验证的结果对象，其中包含所有验证错误信息；，如果没有这个，则会给出异常类名，没有具体的验证信息
//      */
// //    @PostMapping("/test")
// //    @ApiOperationLog(description = "测试接口")
// //    public ResponseEntity<String> test(@RequestBody @Validated User user, BindingResult bindingResult){
// //
// //        // 是否存在校验错误
// //        if(bindingResult.hasErrors()){
// //            // 不懂，先抄再理解
// //            String errMessage = bindingResult.getFieldErrors().stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(", "));
// //            return ResponseEntity.badRequest().body(errMessage);
// //        }
// //        return ResponseEntity.ok("参数校验通过");
// //    }
//
// //     用我们自定义的 response 工具
// //    @PostMapping("/test")
// //    @ApiOperationLog(description = "测试接口")
// //    public Response<String> test(@RequestBody @Validated User user, BindingResult bindingResult) {
// //        // 是否存在校验错误
// //        if(bindingResult.hasErrors()){
// //            String errMessage = bindingResult.getFieldErrors().stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(", "));
// //            return Response.fail(errMessage);
// //        }
// //        return Response.success();
// //    }
//
//     // @PostMapping("/admin/test")
//     // @ApiOperationLog(description = "测试接口")
//     // @ApiOperation(value = "测试接口")
//     // public Response<String> test(@RequestBody @Validated User user){
//     //     log.info("dnegluchenggong ");
//     //     return Response.success();
//     // }
//
//     @PostMapping("/admin/test")
//     @ApiOperationLog(description = "测试接口")
//     @ApiOperation(value = "测试接口")
//     public Response test(@RequestBody @Validated User user) {
//         // 打印入参
//         log.info(JsonUtil.toJsonString(user));
//
//         // 设置三种日期字段值
//         // user.setCreateTime(LocalDateTime.now());
//         // user.setUpdateDate(LocalDate.now());
//         // user.setTime(LocalTime.now());
//
//         return Response.success(user);
//     }
//
// //    @PostMapping("/test")
// //    @ApiOperationLog(description = "测试接口")
// //    public Response test(@RequestBody @Validated User user, BindingResult bindingResult) {
// //        // 主动定义一个运行时异常，分母不能为零
// //        int i = 1 / 0;
// //        return Response.success();
// //    }
//
// }

package com.subinuer.weblog.web.controller;

import com.subinuer.weblog.common.utils.JsonUtil;
import com.subinuer.weblog.common.utils.Response;
import com.subinuer.weblog.web.model.User;
import com.subinuer.weblog.common.aspect.ApiOperationLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;


@RestController
@Slf4j
@Api(tags = "首页模块")
public class TestController {

    @PostMapping("/admin/test")
    @ApiOperationLog(description = "测试接口1")
    @ApiOperation(value = "测试接口")
    public Response test(@RequestBody @Validated User user) {
        // 打印入参
        log.info(JsonUtil.toJsonString(user));

        // 设置三种日期字段值
        // user.setCreateTime(LocalDateTime.now());
        // user.setUpdateDate(LocalDate.now());
        // user.setTime(LocalTime.now());

        return Response.success(user);
    }

}
