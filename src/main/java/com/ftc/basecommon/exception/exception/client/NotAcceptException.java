package com.ftc.basecommon.exception.exception.client;

import cn.hutool.core.util.StrUtil;
import com.ftc.basecommon.exception.template.message.client.ClientExceptionMessageTemplate;

/**
 * @author: 冯铁城 [17615007230@163.com]
 * @date: 2022-12-15 21:53:44
 * @describe: 数据不接受异常
 */
public class NotAcceptException extends RuntimeException {

    /**
     * 初始化数据不接受异常->查询数据已存在
     *
     * @param id       数据主键ID
     * @param typeName 数据类型名称
     */
    public NotAcceptException(int id, String typeName) {
        super(StrUtil.format(
                ClientExceptionMessageTemplate.NOT_ACCEPT,
                StrUtil.format(ClientExceptionMessageTemplate.DATA_EXIST_BY_ID, id, typeName)
        ));
    }
}
