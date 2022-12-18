package com.ftc.basecommon.template.log.error;

/**
 * @author: 冯铁城 [17615007230@163.com]
 * @date: 2022-12-17 10:41:34
 * @describe: 服务端异常日志模板
 */
public final class ServerExceptionTemplate {

    /**
     * 数据不存在异常日志模板
     */
    public final static String DATA_NOT_EXIST_BY_ID = "[数据不存在异常] 主键ID:[{}] 数据类型:[{}]";

    /**
     * 数据已存在异常日志模板
     */
    public final static String DATA_EXIST_BY_ID = "[数据已存在异常] 主键ID:[{}] 数据类型:[{}]";

    /**
     * 保存数据异常日志模板
     */
    public final static String SAVE_DATA = "[保存数据异常] 数据类型:[{}]";

    /**
     * 更新数据异常日志模板
     */
    public final static String UPDATE_DATA = "[更新数据异常] 数据类型:[{}]";
}
