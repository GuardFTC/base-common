package com.ftc.basecommon.exception.server;

import cn.hutool.core.util.StrUtil;
import com.ftc.basecommon.template.log.error.ServerExceptionTemplate;

/**
 * @author: 冯铁城 [17615007230@163.com]
 * @date: 2022-12-15 21:53:44
 * @describe: 数据更新异常
 */
public class UpdateException extends RuntimeException {

    /**
     * 初始化数据更新异常
     *
     * @param tClass 数据类型
     * @param <T>    数据泛型类
     */
    public <T> UpdateException(Class<T> tClass) {
        super(StrUtil.format(ServerExceptionTemplate.UPDATE_DATA, tClass));
    }
}
