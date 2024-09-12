package com.subinuer.weblog.common.domain.dos;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/*
 * 这个类管理 数据库中表 t_user
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("t_user")
public class UserDO {
    @TableId(type = IdType.AUTO)
    private long id;

    private String username;
    private String password;
    private Date createTime;
    private Date updateTime;
    private Boolean isDeleted;

}


/*
    CREATE TABLE `t_user` (
      `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
      `username` varchar(60) NOT NULL COMMENT '用户名',
      `password` varchar(60) NOT NULL COMMENT '密码',
      `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
      `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后一次更新时间',
      `is_deleted` tinyint(2) NOT NULL DEFAULT '0' COMMENT '逻辑删除：0：未删除 1：已删除',
      PRIMARY KEY (`id`) USING BTREE,
      UNIQUE KEY `uk_username` (`username`) USING BTREE
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';
 */