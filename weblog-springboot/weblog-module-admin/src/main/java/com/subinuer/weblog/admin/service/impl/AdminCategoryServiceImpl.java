package com.subinuer.weblog.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.subinuer.weblog.admin.model.vo.category.AddCategoryReqVO;
import com.subinuer.weblog.admin.service.AdminCategoryService;
import com.subinuer.weblog.common.domain.dos.CategoryDO;
import com.subinuer.weblog.common.domain.mapper.CategoryMapper;
import com.subinuer.weblog.common.enums.ResponseCodeEnum;
import com.subinuer.weblog.common.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Objects;

@Component
public class AdminCategoryServiceImpl implements AdminCategoryService {
    @Autowired
    private CategoryMapper categoryMapper;



    @Override
    public Response addCategory(AddCategoryReqVO addCategoryReqVO) {
        // 从请求中获取用户提交的分类名称
        String newCategoryName = addCategoryReqVO.getName();
        if(newCategoryName.isEmpty()){
            return Response.fail("分类名不能为空", "10001");
        }
        if(newCategoryName.length() > 10){
            return Response.fail("分类名长度应 >= 10 位", "10001");
        }

        QueryWrapper<CategoryDO> wrapper = new QueryWrapper<>();
        wrapper.eq("name", newCategoryName);
        CategoryDO categoryDO = categoryMapper.selectOne(wrapper);

        // 查看数据库中是否已存在该分类，没有再添加
        if(Objects.isNull(categoryDO)){
            // 插入
            CategoryDO categoryDO1 = CategoryDO.builder()
                    .name(newCategoryName)
                    .updateTime(new Date())
                    .createTime(new Date())
                    .build();
            if(categoryMapper.insert(categoryDO1) == 1){
                return Response.success();
            }
            return Response.fail("插入失败", "10001");
        }else {
            // 已经存在了
            return Response.fail(ResponseCodeEnum.CATEGORY_NAME_EXISTED);
        }
    }
}
