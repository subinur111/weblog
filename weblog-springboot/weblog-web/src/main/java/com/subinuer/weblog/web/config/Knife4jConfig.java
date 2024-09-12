package com.subinuer.weblog.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

@Configuration                 // 指定了 Knife4jConfig 这个类为配置类
@EnableSwagger2WebMvc          // 启用 Swagger2
@Profile("dev")                // 只在这个环境下生效
public class Knife4jConfig {
    @Bean("webApi")
    public Docket createApiDoc(){
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(buildApiInfo())
                .groupName("web 前台接口")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.subinuer.weblog.web.controller"))
                .paths(PathSelectors.any())
                .build();
        return docket;
    }

    private ApiInfo buildApiInfo(){
        return new ApiInfoBuilder()
                .title("Weblog 博客前台接口文档") // 标题
                .description("Weblog 是一款由 Spring Boot + Vue 3.2 + Vite 4.3 开发的前后端分离博客。") // 描述
                .termsOfServiceUrl("https://www.subinuer.com/") // API 服务条款
                .contact(new Contact("subinuer", "https://www.subinuer.com", "111@qq.com")) // 联系人
                .version("1.0") // 版本号
                .build();
    }
}
