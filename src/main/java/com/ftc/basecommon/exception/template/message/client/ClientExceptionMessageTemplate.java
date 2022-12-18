package com.ftc.basecommon.exception.template.message.client;

/**
 * @author: 冯铁城 [17615007230@163.com]
 * @date: 2022-12-17 10:41:34
 * @describe: 客户端异常消息模板
 */
public final class ClientExceptionMessageTemplate {

    /**
     * 数据不接受异常消息模板
     */
    public final static String NOT_ACCEPT = "接口流程因参数异常中断,异常原因:[{}]";

    /**
     * 数据未找到异常消息模板
     */
    public final static String NOT_FOUND_BY_ID = "根据主键ID:[{}]获取类型:[{}]数据,未找到";

    /**
     * 数据已存在异常日志模板
     */
    public final static String DATA_EXIST_BY_ID = "根据主键ID:[{}]获取类型:[{}]数据,已存在";
}
