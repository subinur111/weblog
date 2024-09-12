package com.subinuer.weblog.common.domain.mapper;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.subinuer.weblog.common.domain.dos.UserDO;

// 这是连数据库的，，用来管理的表 在定义的 UserDO 中
public interface UserMapper extends BaseMapper<UserDO> {

    // 接口中可以实现方法：1. default修饰的默认方法 2. static修饰的静态方法
    // 根据用户名修改密码的方法
    default int updatePasswordByUsername(String userName, String password){
        UpdateWrapper<UserDO> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("username", userName).set("password", password);
        // 调用 BaseMapper 的 update 方法
        return update(null, updateWrapper);
    }

}


/**
 * MyBatis plus 中的条件构造器 Wrapper
 * UpdateWrapper：用于构造更新条件，可以在更新数据时指定条件
 * eq 方法是 MyBatis-Plus 中用于构建查询条件的基本方法之一，它用于设置单个字段的相等条件
 */
