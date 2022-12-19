package com.ftc.basecommon.redis;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * @author: 冯铁城 [17615007230@163.com]
 * @date: 2022-12-19 15:17:53
 * @describe: Redis配置
 */
@Configuration
public class RedisConfig {

    @Primary
    @Bean(name = "primaryRedisProperties")
    @ConfigurationProperties(prefix = "redis.primary")
    public RedisConfigProperties primaryRedisProperties() {
        return new RedisConfigProperties();
    }

    @Bean(name = "secondaryRedisProperties")
    @ConfigurationProperties(prefix = "redis.secondary")
    public RedisConfigProperties secondaryRedisProperties() {
        return new RedisConfigProperties();
    }
}
