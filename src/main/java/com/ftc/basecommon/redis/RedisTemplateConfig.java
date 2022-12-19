package com.ftc.basecommon.redis;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

/**
 * @author: 冯铁城 [17615007230@163.com]
 * @date: 2022-12-19 15:22:21
 * @describe: RedisTemplate配置类
 */
@Configuration
@RequiredArgsConstructor
public class RedisTemplateConfig {

    private final RedisConfig redisConfig;

    @Primary
    @Bean(name = "primaryRedisTemplate")
    public RedisTemplate<String, Object> primaryRedisTemplate() {

        //1.创建连接工厂
        RedisConfigProperties primaryRedisProperties = redisConfig.primaryRedisProperties();
        LettuceConnectionFactory connectionFactory = this.lettuceConnectionFactory(primaryRedisProperties);

        //2.创建RedisTemplate返回
        return this.createRedisTemplate(connectionFactory);
    }

    @Bean(name = "secondaryRedisTemplate")
    public RedisTemplate<String, Object> secondaryRedisTemplate() {

        //1.创建连接工厂
        RedisConfigProperties secondaryRedisProperties = redisConfig.secondaryRedisProperties();
        LettuceConnectionFactory connectionFactory = this.lettuceConnectionFactory(secondaryRedisProperties);

        //2.创建RedisTemplate返回
        return this.createRedisTemplate(connectionFactory);
    }

    /**
     * 创建lettuce连接池工厂
     *
     * @param redisConfigProperties Redis配置
     * @return lettuce 连接池工厂
     */
    private LettuceConnectionFactory lettuceConnectionFactory(RedisConfigProperties redisConfigProperties) {

        //1.线程池设置
        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        poolConfig.setMaxTotal(redisConfigProperties.getMaxActive());
        poolConfig.setMaxWait(Duration.ofMillis(redisConfigProperties.getMaxWait()));
        poolConfig.setMaxIdle(redisConfigProperties.getMaxIdle());
        poolConfig.setMinIdle(redisConfigProperties.getMinIdle());

        //2.线程池客户端设置
        LettuceClientConfiguration clientConfiguration = LettucePoolingClientConfiguration
                .builder()
                .commandTimeout(Duration.ofSeconds(redisConfigProperties.getTimeout()))
                .poolConfig(poolConfig)
                .build();

        //3.Redis基础设置
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
        configuration.setHostName(redisConfigProperties.getHost());
        configuration.setDatabase(redisConfigProperties.getDatabase());
        configuration.setPassword(redisConfigProperties.getPassword());
        configuration.setPort(redisConfigProperties.getPort());

        //4.返回连接池工厂
        LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(configuration, clientConfiguration);
        lettuceConnectionFactory.setShareNativeConnection(true);
        lettuceConnectionFactory.afterPropertiesSet();
        return lettuceConnectionFactory;
    }

    /**
     * 创建RedisTemplate并设置序列化规则
     *
     * @param redisConnectionFactory redis连接池
     */
    private RedisTemplate<String, Object> createRedisTemplate(RedisConnectionFactory redisConnectionFactory) {

        //1.创建Redis连接模板
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();

        //2.设置连接工厂
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        //3.设置keyValue序列化规则
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());

        //4.创建Hash序列化规则
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        jackson2JsonRedisSerializer.setObjectMapper(om);

        //5.设置HashKeyValue序列化规则
        redisTemplate.setHashKeySerializer(jackson2JsonRedisSerializer);
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);

        //6.返回
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
}
