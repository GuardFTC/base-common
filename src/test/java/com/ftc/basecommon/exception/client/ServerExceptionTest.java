package com.ftc.basecommon.exception.client;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.json.JSONObject;
import com.ftc.basecommon.exception.exception.server.SaveException;
import com.ftc.basecommon.exception.exception.server.UpdateException;
import org.junit.jupiter.api.Test;

class ServerExceptionTest {

    @Test
    void testSaveExceptionException() {

        //1.创建异常
        SaveException saveException = new SaveException(new JSONObject());

        //2.校验
        String errorMessage = "数据:[{}]保存异常";
        Assert.isTrue(errorMessage.equals(saveException.getMessage()));

        //3.重置异常
        saveException = new SaveException(CollUtil.newArrayList(1, 2, 3));

        //4.校验
        errorMessage = "数据:[[1,2,3]]批量保存异常";
        Assert.isTrue(errorMessage.equals(saveException.getMessage()));
    }

    @Test
    void testUpdateExceptionException() {

        //1.创建异常
        UpdateException updateException = new UpdateException(new JSONObject());

        //2.校验
        String errorMessage = "数据:[{}]更新异常";
        Assert.isTrue(errorMessage.equals(updateException.getMessage()));

        //3.重置异常
        updateException = new UpdateException(CollUtil.newArrayList(1, 2, 3));

        //4.校验
        errorMessage = "数据:[[1,2,3]]批量更新异常";
        Assert.isTrue(errorMessage.equals(updateException.getMessage()));
    }
}