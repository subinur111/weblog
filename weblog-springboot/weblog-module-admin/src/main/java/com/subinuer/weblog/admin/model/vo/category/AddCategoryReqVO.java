package com.subinuer.weblog.admin.model.vo.category;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AddCategoryReqVO {
    @NotBlank(message = "分类名称不能为空")
    private String name;
}
