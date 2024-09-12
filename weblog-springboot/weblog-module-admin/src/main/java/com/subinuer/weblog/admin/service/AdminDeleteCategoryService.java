package com.subinuer.weblog.admin.service;


import com.subinuer.weblog.admin.model.vo.category.DeleteCategoryReqVO;
import com.subinuer.weblog.common.utils.Response;

public interface AdminDeleteCategoryService {
    Response deleteCategory(DeleteCategoryReqVO deleteCategoryReqVO);
}
