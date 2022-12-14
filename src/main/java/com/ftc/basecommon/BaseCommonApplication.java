package com.ftc.basecommon;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author: 冯铁城 [17615007230@163.com]
 * @date: 2022-12-05 20:07:49
 * @describe: 项目启动类
 */
@EnableSwagger2Doc
@SpringBootApplication
public class BaseCommonApplication {
    public static void main(String[] args) {
        SpringApplication.run(BaseCommonApplication.class, args);
    }
}
