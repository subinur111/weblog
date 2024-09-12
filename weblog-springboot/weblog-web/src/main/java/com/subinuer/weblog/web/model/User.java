package com.subinuer.weblog.web.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

@Data
@ApiModel(value = "用户实体类")
public class User {
    // 用户名
    @NotBlank(message = "姓名不能为空")
    @ApiModelProperty(value = "用户名")
    private String username;

    // 性别
    @NotNull(message = "性别不能为空")
    private Integer sex;

    @NotNull(message = "年龄不能为空")
    @Min(value = 18, message = "年龄不能小于18")
    @Max(value = 100, message = "年龄不能大于100")
    private Integer age;

    // 邮箱
    @NotBlank(message = "邮箱不能为空")
    @Email(message = "邮箱格式不正确")  // 注解确保邮箱格式正确
    private String email;

    // public void setCreateTime(LocalDateTime now) {
    // }

//    public String getName() {
//        return username;
//    }
//
//    public int getSex() {
//        return sex;
//    }
}