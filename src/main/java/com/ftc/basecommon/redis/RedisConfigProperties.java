package com.ftc.basecommon.redis;

import lombok.Data;

/**
 * @author: 冯铁城 [17615007230@163.com]
 * @date: 2022-12-19 15:10:32
 * @describe: Redis配置属性类
 */
@Data
public class RedisConfigProperties {

    /**
     * 主机
     */
    private String host;

    /**
     * 端口
     */
    private int port;

    /**
     * 密码
     */
    private String password;

    /**
     * 数据库
     */
    private int database;

    /**
     * 链接超时时间/ms
     */
    private int timeout;

    /**
     * 最大激活连接数
     */
    private int maxActive;

    /**
     * 链接最大等待时间/ms
     */
    private int maxWait;

    /**
     * 最大空闲连接数
     */
    private int maxIdle;

    /**
     * 最小空闲连接数
     */
    private int minIdle;
}
