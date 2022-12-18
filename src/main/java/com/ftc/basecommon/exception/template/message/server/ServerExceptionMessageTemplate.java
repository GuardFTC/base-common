package com.ftc.basecommon.exception.template.message.server;

/**
 * @author: 冯铁城 [17615007230@163.com]
 * @date: 2022-12-17 10:41:34
 * @describe: 服务端异常消息模板
 */
public final class ServerExceptionMessageTemplate {

    /**
     * 保存数据异常消息模板
     */
    public final static String SAVE_DATA = "数据:[{}]保存异常";

    /**
     * 批量保存数据异常消息模板
     */
    public final static String SAVE_BATCH_DATA = "数据:[{}]批量保存异常";

    /**
     * 更新数据异常消息模板
     */
    public final static String UPDATE_DATA = "数据:[{}]更新异常";

    /**
     * 批量更新数据异常消息模板
     */
    public final static String UPDATE_BATCH_DATA = "数据:[{}]批量更新异常";
}
