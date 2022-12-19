package com.ftc.basecommon.redis;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;

@SpringBootTest
class RedisUtilTest {

    @Resource
    RedisUtil redisUtil;

    @Resource
    @Qualifier("secondaryRedisTemplate")
    private RedisTemplate<String, Object> secondaryRedisTemplate;

    @Test
    void testAll() {

        //1.set键值对
        redisUtil.set("primary", "primary");

        //2.切换数据源为备数据源
        try {
            redisUtil.setRedisTemplate(secondaryRedisTemplate);
            redisUtil.set("secondary", "secondary");
        } finally {
            redisUtil.init();
        }

        //3.再次set键值对
        redisUtil.set("third", "third");
    }
}