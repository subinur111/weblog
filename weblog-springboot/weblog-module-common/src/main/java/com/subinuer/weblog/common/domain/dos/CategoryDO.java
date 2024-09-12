package com.subinuer.weblog.common.domain.dos;

import java.util.Date;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@TableName("t_category")
public class CategoryDO {
    @TableId(value = "id", type = IdType.AUTO)  // 数据库id自增
    private long id;
    private String name;
    private Date createTime;
    private Date updateTime;
    private boolean isDeleted;
}
