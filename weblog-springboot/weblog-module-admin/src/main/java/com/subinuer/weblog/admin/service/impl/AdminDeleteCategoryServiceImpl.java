package com.subinuer.weblog.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.subinuer.weblog.admin.model.vo.category.DeleteCategoryReqVO;
import com.subinuer.weblog.admin.service.AdminDeleteCategoryService;
import com.subinuer.weblog.common.domain.dos.CategoryDO;
import com.subinuer.weblog.common.domain.mapper.CategoryMapper;
import com.subinuer.weblog.common.utils.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@Slf4j
public class AdminDeleteCategoryServiceImpl implements AdminDeleteCategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public Response deleteCategory(DeleteCategoryReqVO deleteCategoryReqVO) {
        // 判断是否存在
        // 条件封装器 wrapper
        LambdaQueryWrapper<CategoryDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CategoryDO::getName, deleteCategoryReqVO.getName());

        // 接收sql语句的 返回
        CategoryDO dos = categoryMapper.selectOne(wrapper);

        if(Objects.isNull(dos)){
            log.info("该类不存在，删除失败");
            return Response.fail("该类不存在，删除失败", "10001");
        }

        // 调用 delete 方法进行删除
        if(categoryMapper.delete(wrapper) > 0){
            return Response.success("删除成功");
        }
        return Response.fail("删除失败");
    }
}
