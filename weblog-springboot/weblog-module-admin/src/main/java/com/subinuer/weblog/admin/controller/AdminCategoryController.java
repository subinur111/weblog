package com.subinuer.weblog.admin.controller;

import com.subinuer.weblog.admin.model.vo.category.AddCategoryReqVO;
import com.subinuer.weblog.admin.model.vo.category.DeleteCategoryReqVO;
import com.subinuer.weblog.admin.model.vo.category.SearchCategoryListReqVO;
import com.subinuer.weblog.admin.service.AdminCategoryService;
import com.subinuer.weblog.admin.service.AdminDeleteCategoryService;
import com.subinuer.weblog.admin.service.AdminSearchCategoryListService;
import com.subinuer.weblog.common.utils.CatListResponse;
import com.subinuer.weblog.common.utils.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/admin")
public class AdminCategoryController {
    /**
     * 新增请求
     * @param addCategoryReqVO
     * @return
     */
    @Autowired
    private AdminCategoryService adminCategoryService;

    @RequestMapping(value = "/category/add")
    public Response addCategoryByName(@RequestBody AddCategoryReqVO addCategoryReqVO){
        log.info("接收到新增分类请求");
        return adminCategoryService.addCategory(addCategoryReqVO);
    }

    /**
     * 分页请求
     */
    @Autowired
    private AdminSearchCategoryListService adminSearchCategoryListService;

    @RequestMapping("/category/list")
    public CatListResponse searchCategory(@RequestBody SearchCategoryListReqVO searchCategoryListReqVO){
        // 处理请求的方法
        log.info("获取到请求分类列表的请求" + searchCategoryListReqVO.getStartCreateDate() + "   " + searchCategoryListReqVO.getEndCreateDate());
        return adminSearchCategoryListService.searchCategoryList(searchCategoryListReqVO);
    }

    /**
     * 删除请求
     */
    @Autowired
    private AdminDeleteCategoryService adminDeleteCategoryService;

    @RequestMapping("/category/delete")
    public Response deleteCategory(@RequestBody DeleteCategoryReqVO deleteCategoryReqVO){
        log.info("接收到删除请求，要删除的分类是： " + deleteCategoryReqVO.getName());
        return adminDeleteCategoryService.deleteCategory(deleteCategoryReqVO);
    }

}
