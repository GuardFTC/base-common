package com.ftc.basecommon.test;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author: 冯铁城 [17615007230@163.com]
 * @date: 2022-12-18 14:48:34
 * @describe: 测试实体类
 */
@Data
public class TestEntity implements Serializable {

    @ApiModelProperty("年龄字符串")
    @NotBlank(message = "年龄字符串不能为空")
    @Min(value = 3, message = "年龄字符串不能小于3")
    private String ageStr;
}
