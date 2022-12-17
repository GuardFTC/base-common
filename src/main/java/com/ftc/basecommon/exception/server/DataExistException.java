package com.ftc.basecommon.exception.server;

import cn.hutool.core.util.StrUtil;
import com.ftc.basecommon.template.log.error.ServerExceptionTemplate;

/**
 * @author: 冯铁城 [17615007230@163.com]
 * @date: 2022-12-15 21:53:44
 * @describe: 数据已存在异常
 */
public class DataExistException extends RuntimeException {

    /**
     * 初始化数据已存在异常
     *
     * @param id     数据主键ID
     * @param tClass 数据类型
     * @param <T>    数据泛型类
     */
    public <T> DataExistException(int id, Class<T> tClass) {
        super(StrUtil.format(ServerExceptionTemplate.DATA_EXIST_BY_ID, id, tClass));
    }
}
