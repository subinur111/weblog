package com.subinuer.weblog.common.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.subinuer.weblog.common.domain.dos.CategoryDO;

/**
 * 这是连数据库的，，用来管理的表 在定义的 CategoryDO 中
 */
public interface CategoryMapper extends BaseMapper<CategoryDO> {

    /**
     * 新增 分类
     * 接收 种类名称 插入数据库
     */
    // default CategoryDO getCategoryByName(String name){
    //
    // }

    // Page<CategoryDO> selectCategoryPage(Page<CategoryDO> page);

}
