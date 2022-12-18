package com.ftc.basecommon.exception.template.log.client;

/**
 * @author: 冯铁城 [17615007230@163.com]
 * @date: 2022-12-17 10:41:34
 * @describe: 客户端异常日志模板
 */
public final class ClientExceptionLogTemplate {

    /**
     * 基础日志模板
     */
    private final static String BASE_TEMPLATE = " IP=[{}] METHOD=[{}] URL=[{}] EXCEPTION_MESSAGE=[{}]";

    /**
     * 请求参数异常日志模板
     */
    public final static String BAD_REQUEST = "[请求参数异常]" + BASE_TEMPLATE;

    /**
     * 参数不接受异常日志模板
     */
    public final static String NOT_ACCEPT = "[参数不接受异常]" + BASE_TEMPLATE;

    /**
     * 数据未找到异常日志模板
     */
    public final static String NOT_FOUND = "[数据未找到异常]" + BASE_TEMPLATE;
}
