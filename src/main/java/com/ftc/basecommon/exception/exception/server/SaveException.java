package com.ftc.basecommon.exception.exception.server;

import cn.hutool.core.util.StrUtil;
import com.ftc.basecommon.exception.template.message.server.ServerExceptionMessageTemplate;

import java.util.List;

/**
 * @author: 冯铁城 [17615007230@163.com]
 * @date: 2022-12-15 21:53:44
 * @describe: 数据保存异常
 */
public class SaveException extends RuntimeException {

    /**
     * 初始化数据保存异常
     *
     * @param data 数据
     * @param <T>  数据泛型类
     */
    public <T> SaveException(T data) {
        super(StrUtil.format(ServerExceptionMessageTemplate.SAVE_DATA, data));
    }

    /**
     * 初始化数据保存异常
     *
     * @param dataList 数据集合
     * @param <T>      数据泛型类
     */
    public <T> SaveException(List<T> dataList) {
        super(StrUtil.format(ServerExceptionMessageTemplate.SAVE_BATCH_DATA, dataList));
    }
}
