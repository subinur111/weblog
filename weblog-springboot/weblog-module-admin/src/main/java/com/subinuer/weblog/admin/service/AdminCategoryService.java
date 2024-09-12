package com.subinuer.weblog.admin.service;

import com.subinuer.weblog.admin.model.vo.category.AddCategoryReqVO;
import com.subinuer.weblog.common.utils.Response;

public interface AdminCategoryService {
    // 新增分类
    Response addCategory(AddCategoryReqVO addCategoryReqVO);
}
