package com.subinuer.weblog.admin.model.vo.category;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

/**
 * 这个类是 前端查询请求传递的 json 中的data
 */

@Data
public class SearchCategoryListReqVO {
    // 当前页码
    private int currentPage;

    // 每页要展示的数量
    private int size;

    @NotBlank(message = "输入不能为空")
    private String categoryName;

    private LocalDate startCreateDate;

    private LocalDate endCreateDate;

}
