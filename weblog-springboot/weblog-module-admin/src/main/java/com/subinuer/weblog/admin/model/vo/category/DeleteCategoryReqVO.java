package com.subinuer.weblog.admin.model.vo.category;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 分类 删除接口 请求
 */
@Data
public class DeleteCategoryReqVO {
    @NotBlank(message = "不能为空")
    private String name;

}
