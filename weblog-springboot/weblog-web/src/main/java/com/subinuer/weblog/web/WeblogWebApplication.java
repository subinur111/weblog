package com.subinuer.weblog.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.subinuer.weblog.*"})       //  多模块项目中，必需手动指定扫描 subinuer 包下面的所有类 (这里报错冗余声明)
public class WeblogWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(WeblogWebApplication.class, args);
    }

}
