package com.ftc.basecommon.exception.template.log.server;

/**
 * @author: 冯铁城 [17615007230@163.com]
 * @date: 2022-12-17 10:41:34
 * @describe: 服务端异常日志模板
 */
public final class ServerExceptionLogTemplate {

    /**
     * 基础日志模板
     */
    private final static String BASE_TEMPLATE = " EXCEPTION_MESSAGE=[{}]";

    /**
     * 保存数据异常日志模板
     */
    public final static String SAVE_DATA = "[保存数据异常]" + BASE_TEMPLATE;

    /**
     * 更新数据异常日志模板
     */
    public final static String UPDATE_DATA = "[更新数据异常]" + BASE_TEMPLATE;
}
