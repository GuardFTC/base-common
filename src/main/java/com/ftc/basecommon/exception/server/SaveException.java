package com.ftc.basecommon.exception.server;

import cn.hutool.core.util.StrUtil;
import com.ftc.basecommon.template.log.error.ServerExceptionTemplate;

/**
 * @author: 冯铁城 [17615007230@163.com]
 * @date: 2022-12-15 21:53:44
 * @describe: 数据保存异常
 */
public class SaveException extends RuntimeException {

    /**
     * 初始化数据保存异常
     *
     * @param tClass 数据类型
     * @param <T>    数据泛型类
     */
    public <T> SaveException(Class<T> tClass) {
        super(StrUtil.format(ServerExceptionTemplate.SAVE_DATA, tClass));
    }
}
