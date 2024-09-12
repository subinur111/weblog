package com.subinuer.weblog.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.subinuer.weblog.admin.model.vo.category.SearchCategoryListReqVO;
import com.subinuer.weblog.admin.model.vo.category.SearchCategoryListRespVO;
import com.subinuer.weblog.admin.service.AdminSearchCategoryListService;
import com.subinuer.weblog.common.domain.dos.CategoryDO;
import com.subinuer.weblog.common.domain.mapper.CategoryMapper;
import com.subinuer.weblog.common.utils.CatListResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class AdminSearchCategoryListServiceImpl implements AdminSearchCategoryListService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public CatListResponse searchCategoryList(SearchCategoryListReqVO searchCategoryListReqVO) {
        long currentPage = searchCategoryListReqVO.getCurrentPage();
        long size = searchCategoryListReqVO.getSize();
        // 定义需要的参数
        Page<CategoryDO> page = new Page<>(currentPage, size);

        // 发起查询
        String name = searchCategoryListReqVO.getCategoryName();
        LocalDate startCreateDate = searchCategoryListReqVO.getStartCreateDate();
        LocalDate endCreateDate = searchCategoryListReqVO.getEndCreateDate();

        // 从中间选出时间在 范围内 中的
        LambdaQueryWrapper<CategoryDO> lambdaQueryWrapper = new LambdaQueryWrapper<>();

        if(startCreateDate == null || endCreateDate == null){
            lambdaQueryWrapper.like(CategoryDO::getName, name)
                    .orderByAsc(CategoryDO::getId);
        }else if(Objects.nonNull(name)){
            lambdaQueryWrapper.like(CategoryDO::getName, name)
                    .ge(CategoryDO::getCreateTime, startCreateDate)
                    .le(CategoryDO::getCreateTime, endCreateDate)
                    .orderByAsc(CategoryDO::getId);
        }else {
            lambdaQueryWrapper.orderByAsc(CategoryDO::getId);
        }

        // 分页展示
        Page<CategoryDO> categoryDOPage =  categoryMapper.selectPage(page, lambdaQueryWrapper);

        List<CategoryDO> categoryDOS = categoryDOPage.getRecords();

        // 将结果转换为 responseVO 中
        List<SearchCategoryListRespVO> vos = new ArrayList<>();
        if(!categoryDOS.isEmpty()){
            long i = 0L;
            for(CategoryDO dos: categoryDOS){
                SearchCategoryListRespVO tmp = new SearchCategoryListRespVO();
                tmp.setId(dos.getId());
                tmp.setName(dos.getName());
                tmp.setCreatetime(dos.getCreateTime());
                vos.add(tmp);
                i++;
            }
        }

        return CatListResponse.success(categoryDOPage, vos);
    }
}
