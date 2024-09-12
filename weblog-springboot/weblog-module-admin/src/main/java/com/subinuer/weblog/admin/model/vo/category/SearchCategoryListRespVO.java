package com.subinuer.weblog.admin.model.vo.category;

import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
public class SearchCategoryListRespVO {
    private long id;
    private String name;
    private Date createtime;
}
