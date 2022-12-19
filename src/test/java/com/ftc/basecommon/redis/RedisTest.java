package com.ftc.basecommon.redis;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;

@SpringBootTest
class RedisTest {

    @Resource
    @Qualifier("primaryRedisTemplate")
    private RedisTemplate<String, Object> primaryRedisTemplate;

    @Resource
    @Qualifier("secondaryRedisTemplate")
    private RedisTemplate<String, Object> secondaryRedisTemplate;

    @Test
    void testMultiTemplate() {

        //1.主数据源写入数据
        primaryRedisTemplate.opsForValue().set("primary", "value");

        //2.备数据源写入数据
        secondaryRedisTemplate.opsForValue().set("secondary", "value");
    }
}