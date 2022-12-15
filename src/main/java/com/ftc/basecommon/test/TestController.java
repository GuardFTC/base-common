package com.ftc.basecommon.test;

import cn.hutool.core.lang.Console;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author: 冯铁城 [17615007230@163.com]
 * @date: 2022-12-15 10:16:32
 * @describe: 测试Controller
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/rest/test")
public class TestController {

    @PostMapping
    public void add(@Validated @RequestBody TestEntity testEntity) {
        Console.log(testEntity);
    }

    @Data
    private static class TestEntity implements Serializable {

        @ApiModelProperty("年龄字符串")
        @NotBlank(message = "年龄字符串不能为空")
        @Min(value = 3, message = "年龄字符串不能小于3")
        private String ageStr;
    }
}
