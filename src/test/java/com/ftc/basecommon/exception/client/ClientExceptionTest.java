package com.ftc.basecommon.exception.client;

import cn.hutool.core.lang.Assert;
import com.ftc.basecommon.exception.exception.client.NotAcceptException;
import com.ftc.basecommon.exception.exception.client.NotFoundException;
import org.junit.jupiter.api.Test;

class ClientExceptionTest {

    @Test
    void testNotFoundException() {

        //1.创建异常
        NotFoundException notFoundException = new NotFoundException(Integer.MIN_VALUE, Integer.class.getTypeName());

        //2.校验
        String errorMessage = "根据主键ID:[-2147483648]获取类型:[java.lang.Integer]数据,未找到";
        Assert.isTrue(errorMessage.equals(notFoundException.getMessage()));
    }

    @Test
    void testNotAcceptException() {

        //1.创建异常
        NotAcceptException notAcceptException = new NotAcceptException(Integer.MIN_VALUE, Integer.class.getTypeName());

        //2.校验
        String errorMessage = "接口流程因参数异常中断,异常原因:[根据主键ID:[-2147483648]获取类型:[java.lang.Integer]数据,已存在]";
        Assert.isTrue(errorMessage.equals(notAcceptException.getMessage()));
    }
}