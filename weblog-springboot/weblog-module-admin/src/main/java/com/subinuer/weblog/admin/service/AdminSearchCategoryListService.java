package com.subinuer.weblog.admin.service;

import com.subinuer.weblog.admin.model.vo.category.SearchCategoryListReqVO;
import com.subinuer.weblog.common.utils.CatListResponse;

public interface AdminSearchCategoryListService {
    CatListResponse searchCategoryList(SearchCategoryListReqVO searchCategoryListReqVO);
}
