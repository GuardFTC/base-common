package com.ftc.basecommon.template.log.error;

/**
 * @author: 冯铁城 [17615007230@163.com]
 * @date: 2022-12-17 10:41:34
 * @describe: 服务器异常日志模板
 */
public final class ServerExceptionTemplate {

    /**
     * 数据不存在异常日志模板
     */
    public final static String DATA_NOT_EXIST_BY_ID = "根据id:[{}]获取[{}]类型数据->不存在";

    /**
     * 数据已存在异常日志模板
     */
    public final static String DATA_EXIST_BY_ID = "根据id:[{}]获取[{}]类型数据->已存在";
}
