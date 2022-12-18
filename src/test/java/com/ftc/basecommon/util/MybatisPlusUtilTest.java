package com.ftc.basecommon.util;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ftc.basecommon.exception.server.DataExistException;
import com.ftc.basecommon.exception.server.DataNotExistException;
import com.ftc.basecommon.exception.server.SaveException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

class MybatisPlusUtilTest {

    private final IService<Integer> mockService = Mockito.mock(IService.class);

    @Test
    void saveBatchSuccess() {

        //1.准备参数
        int countThreshold = 5;
        List<Integer> dataList = CollUtil.newArrayList(1, 2, 3, 4, 5, 6);

        //2.模拟行为
        Mockito.when(mockService.saveBatch(Mockito.anyList())).thenReturn(true);

        //3.调用
        MybatisPlusUtil.saveBatch(dataList, mockService, countThreshold);

        //4.验证行为
        Mockito.verify(mockService, Mockito.times(2)).saveBatch(Mockito.anyList());
    }

    @Test
    void saveBatchError() {

        //1.准备参数
        int countThreshold = Integer.MIN_VALUE;
        List<Integer> dataList = CollUtil.newArrayList(Integer.MIN_VALUE);

        //2.模拟行为
        Mockito.when(mockService.saveBatch(Mockito.anyList())).thenReturn(false);

        //3.调用
        SaveException exception = assertThrows(SaveException.class, () -> {
            MybatisPlusUtil.saveBatch(dataList, mockService, countThreshold);
        });

        //4.验证异常信息
        String errorMessage = "[保存数据异常] 数据类型:[class java.lang.Integer]";
        Assert.isTrue(errorMessage.equals(exception.getMessage()));
    }

    @Test
    void getAndCheckNotExistSuccess() {

        //1.准备参数
        int id = Integer.MIN_VALUE;

        //2.模拟行为
        Mockito.when(mockService.getById(Mockito.anyInt())).thenReturn(Integer.MIN_VALUE);

        //3.调用
        Integer result = MybatisPlusUtil.getAndCheckNotExist(id, mockService);

        //4.验证
        Assert.isTrue(Integer.MIN_VALUE == result);
    }

    @Test
    void getAndCheckNotExistError() {

        //1.准备参数
        int id = Integer.MIN_VALUE;

        //2.模拟行为
        Mockito.when(mockService.getById(Mockito.anyInt())).thenReturn(null);

        //3.调用
        DataNotExistException exception = assertThrows(DataNotExistException.class, () -> {
            MybatisPlusUtil.getAndCheckNotExist(id, mockService);
        });

        //4.验证
        String errorMessage = "[数据不存在异常] 主键ID:[-2147483648]";
        Assert.isTrue(exception.getMessage().contains(errorMessage));
    }

    @Test
    void getAndCheckExist() {

        //1.准备参数
        int id = Integer.MIN_VALUE;

        //2.模拟行为
        Mockito.when(mockService.getById(Mockito.anyInt())).thenReturn(null);

        //3.调用
        MybatisPlusUtil.getAndCheckExist(id, mockService);
    }

    @Test
    void getAndCheckExistError() {

        //1.准备参数
        int id = Integer.MIN_VALUE;

        //2.模拟行为
        Mockito.when(mockService.getById(Mockito.anyInt())).thenReturn(Integer.MIN_VALUE);

        //3.调用
        DataExistException exception = assertThrows(DataExistException.class, () -> {
            MybatisPlusUtil.getAndCheckExist(id, mockService);
        });

        //4.验证
        String errorMessage = "[数据已存在异常] 主键ID:[-2147483648] 数据类型:[class java.lang.Integer]";
        Assert.isTrue(errorMessage.equals(exception.getMessage()));
    }
}