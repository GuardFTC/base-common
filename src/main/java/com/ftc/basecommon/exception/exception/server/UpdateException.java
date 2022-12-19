package com.ftc.basecommon.exception.exception.server;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.ftc.basecommon.exception.template.message.server.ServerExceptionMessageTemplate;

import java.util.List;

/**
 * @author: 冯铁城 [17615007230@163.com]
 * @date: 2022-12-15 21:53:44
 * @describe: 数据更新异常
 */
public class UpdateException extends RuntimeException {

    /**
     * 初始化数据更新异常
     *
     * @param data 数据
     * @param <T>  数据泛型类
     */
    public <T> UpdateException(T data) {
        super(StrUtil.format(ServerExceptionMessageTemplate.UPDATE_DATA, JSONUtil.toJsonStr(data)));
    }

    /**
     * 初始化数据更新异常
     *
     * @param dataList 数据集合
     * @param <T>      数据泛型类
     */
    public <T> UpdateException(List<T> dataList) {
        super(StrUtil.format(ServerExceptionMessageTemplate.UPDATE_BATCH_DATA, JSONUtil.toJsonStr(dataList)));
    }
}
