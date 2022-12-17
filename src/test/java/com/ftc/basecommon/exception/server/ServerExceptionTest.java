package com.ftc.basecommon.exception.server;

import cn.hutool.core.lang.Assert;
import org.junit.jupiter.api.Test;

class ServerExceptionTest {

    @Test
    void testDataNotExistException() {

        //1.创建异常
        DataNotExistException dataNotExistException = new DataNotExistException(Integer.MIN_VALUE, Object.class);

        //2.校验
        String errorMessage = "根据id:[-2147483648]获取[class java.lang.Object]类型数据->不存在";
        Assert.isTrue(errorMessage.equals(dataNotExistException.getMessage()));
    }

    @Test
    void testDataExistException() {

        //1.创建异常
        DataExistException dataExistException = new DataExistException(Integer.MIN_VALUE, Object.class);

        //2.校验
        String errorMessage = "根据id:[-2147483648]获取[class java.lang.Object]类型数据->已存在";
        Assert.isTrue(errorMessage.equals(dataExistException.getMessage()));
    }
}