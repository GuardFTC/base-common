package com.ftc.basecommon.test;

import cn.hutool.core.lang.Console;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: 冯铁城 [17615007230@163.com]
 * @date: 2022-12-15 10:16:32
 * @describe: 测试Controller
 */
@Api("测试Controller")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/rest/test")
public class TestController {

    @PostMapping
    @ApiOperation("测试POST添加接口")
    public void add(@Validated @RequestBody TestEntity testEntity) {
        Console.log(testEntity);
    }
}
