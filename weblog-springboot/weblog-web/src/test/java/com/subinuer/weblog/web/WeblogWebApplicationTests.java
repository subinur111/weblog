package com.subinuer.weblog.web;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.subinuer.weblog.common.domain.dos.CategoryDO;
import com.subinuer.weblog.common.domain.dos.UserDO;
import com.subinuer.weblog.common.domain.mapper.CategoryMapper;
import com.subinuer.weblog.common.domain.mapper.UserMapper;
import com.subinuer.weblog.web.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
// 添加 Lombok 的 @Slf4j 日志注解
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@SpringBootTest
@Slf4j
class WeblogWebApplicationTests {

    @Test
    void contextLoads() {
    }

//    @Test
//    void testLog(){
//        log.info("这是一行 Info 级别日志");
//        log.warn("这是一行 Warn 级别日志");
//        log.error("这是一行 Error 级别日志");
//
//        String author = "subinuer";
//        log.info("这是一行带有占位符日志，作者：{}", author);
//
//    }

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CategoryMapper categoryMapper;


   @Test
   void testDataBase(){
       UserDO userDO = UserDO.builder()
               .username("subinuer")
               .password("$2a$10$GDm.Jp8D1IXEWZTd6p3/JO40mTp0bzWLU0/QfAdYrmxlN3UiPXjm.")
               .createTime(new Date())
               .updateTime(new Date())
               .isDeleted(false)
               .build();
       userMapper.selectById(1);
       //
       CategoryDO categoryDO = CategoryDO.builder()
               .name("科幻")
               .createTime(new Date())
               .updateTime(new Date())
               .isDeleted(false)
               .build();
       ;
       System.out.println(categoryMapper.insert(categoryDO));
       // QueryWrapper<CategoryDO> queryWrapper = new QueryWrapper<>();
       // System.out.println(queryWrapper.eq("name", "科普").getSqlSelect());
       // System.out.println();

   }


}
