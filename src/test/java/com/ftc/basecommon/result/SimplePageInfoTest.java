package com.ftc.basecommon.result;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.json.JSONUtil;
import com.github.pagehelper.PageInfo;
import org.junit.jupiter.api.Test;

import java.util.List;

class SimplePageInfoTest {

    @Test
    void initByPageInfo() {

        //1.创建PageInfo
        PageInfo<Integer> pageInfo = new PageInfo<>();
        pageInfo.setPageNum(1);
        pageInfo.setPageSize(10);
        pageInfo.setTotal(2);
        pageInfo.setList(CollUtil.newArrayList(1, 2));

        //2.获取简单分页实体类
        SimplePageInfo<Integer> simplePageInfo = SimplePageInfo.init(pageInfo);

        //3.校验
        String result = "{\"pageNum\":1,\"pageSize\":10,\"total\":2,\"data\":[1,2]}";
        Assert.isTrue(result.equals(JSONUtil.toJsonStr(simplePageInfo)));
    }

    @Test
    void initByDataAndPageNumAndPageSize() {

        //1.循环封装集合
        List<Integer> data = CollUtil.newArrayList();
        for (int i = 1; i <= 95; i++) {
            data.add(i);
        }

        //2.定义参数
        int pageNum = 1;
        int pageSize = 10;

        //3.获取简单分页实体类
        SimplePageInfo<Integer> simplePageInfo = SimplePageInfo.init(data, pageNum, pageSize);

        //4.校验
        String result = "{\"pageNum\":1,\"pageSize\":10,\"total\":95,\"data\":[1,2,3,4,5,6,7,8,9,10]}";
        Assert.isTrue(result.equals(JSONUtil.toJsonStr(simplePageInfo)));

        //5.重置参数
        pageNum = 10;

        //6.再次获取简单分页实体类
        simplePageInfo = SimplePageInfo.init(data, pageNum, pageSize);

        //7.校验
        result = "{\"pageNum\":10,\"pageSize\":10,\"total\":95,\"data\":[91,92,93,94,95]}";
        Assert.isTrue(result.equals(JSONUtil.toJsonStr(simplePageInfo)));

        //8.重置参数
        pageNum = 11;

        //9.再次获取简单分页实体类
        simplePageInfo = SimplePageInfo.init(data, pageNum, pageSize);

        //10.校验
        result = "{\"pageNum\":11,\"pageSize\":10,\"total\":95,\"data\":[]}";
        Assert.isTrue(result.equals(JSONUtil.toJsonStr(simplePageInfo)));
    }
}