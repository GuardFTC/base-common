package com.ftc.basecommon.exception.server;

import cn.hutool.core.lang.Assert;
import org.junit.jupiter.api.Test;

class ServerExceptionTest {

    @Test
    void testDataNotExistException() {

        //1.创建异常
        DataNotExistException dataNotExistException = new DataNotExistException(Integer.MIN_VALUE, Object.class);

        //2.校验
        String errorMessage = "[数据不存在异常] 主键ID:[-2147483648] 数据类型:[class java.lang.Object]";
        Assert.isTrue(errorMessage.equals(dataNotExistException.getMessage()));
    }

    @Test
    void testDataExistException() {

        //1.创建异常
        DataExistException dataExistException = new DataExistException(Integer.MIN_VALUE, Object.class);

        //2.校验
        String errorMessage = "[数据已存在异常] 主键ID:[-2147483648] 数据类型:[class java.lang.Object]";
        Assert.isTrue(errorMessage.equals(dataExistException.getMessage()));
    }
}