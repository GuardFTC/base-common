package com.ftc.basecommon.redis;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author: 冯铁城 [17615007230@163.com]
 * @date: 2022-12-19 16:13:59
 * @describe: Redis工具类
 */
@Setter
@Component
@RequiredArgsConstructor
public class RedisUtil {

    private RedisTemplate<String, Object> redisTemplate;

    private final RedisTemplate<String, Object> primaryRedisTemplate;

    @PostConstruct
    public void init() {
        redisTemplate = primaryRedisTemplate;
    }

    /* -------------------------------------key相关操作----------------------------------- */

    /**
     * 删除key
     *
     * @param key Key
     */
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    /**
     * 批量删除key
     *
     * @param keys Key集合
     */
    public void delete(Collection<String> keys) {
        redisTemplate.delete(keys);
    }

    /**
     * 是否存在key
     *
     * @param key Key
     * @return Key 是否存在
     */
    public boolean hasKey(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    /**
     * 设置过期时间
     *
     * @param key     Key
     * @param timeout 过期时间
     * @param unit    过期时间单位
     * @return 是否设置成功
     */
    public boolean expire(String key, long timeout, TimeUnit unit) {
        return Boolean.TRUE.equals(redisTemplate.expire(key, timeout, unit));
    }

    /**
     * 设置过期时间
     *
     * @param key  Key
     * @param date 指定日期
     * @return 是否设置成功
     */
    public boolean expireAt(String key, Date date) {
        return Boolean.TRUE.equals(redisTemplate.expireAt(key, date));
    }

    /**
     * 移除key的过期时间
     *
     * @param key Key
     * @return 是否移除成功
     */
    public boolean persist(String key) {
        return Boolean.TRUE.equals(redisTemplate.persist(key));
    }

    /**
     * 返回key的剩余的过期时间,指定时间单位
     *
     * @param key  Key
     * @param unit 过期时间单位
     * @return 过期时间
     */
    public Long getExpire(String key, TimeUnit unit) {
        return redisTemplate.getExpire(key, unit);
    }

    /**
     * 返回key的剩余的过期时间,单位ms
     *
     * @param key Key
     * @return 过期时间
     */
    public Long getExpire(String key) {
        return redisTemplate.getExpire(key);
    }

    /**
     * 查找匹配的key
     *
     * @param pattern 匹配公式
     * @return 匹配Key值集合
     */
    public Set<String> keys(String pattern) {
        return redisTemplate.keys(pattern);
    }

    /**
     * 从当前数据库中随机返回一个 key
     *
     * @return 随机Key
     */
    public String randomKey() {
        return redisTemplate.randomKey();
    }

    /**
     * 修改key的名称
     *
     * @param oldKey 原Key值
     * @param newKey 新Key值
     */
    public void rename(String oldKey, String newKey) {
        redisTemplate.rename(oldKey, newKey);
    }

    /**
     * 仅当newKey不存在时,将oldKey改名为newKey
     *
     * @param oldKey 原Key值
     * @param newKey 新Key值
     * @return 是否变更成功
     */
    public boolean renameIfAbsent(String oldKey, String newKey) {
        return Boolean.TRUE.equals(redisTemplate.renameIfAbsent(oldKey, newKey));
    }

    /**
     * 返回key所储存的值的类型
     *
     * @param key Key
     * @return key所储存的值的类型
     */
    public DataType type(String key) {
        return redisTemplate.type(key);
    }

    /**
     * 将当前数据库key移动到给定的数据库当中
     *
     * @param key     Key
     * @param dbIndex 数据库序号
     * @return 是否迁移成功
     */
    public boolean move(String key, int dbIndex) {
        return Boolean.TRUE.equals(redisTemplate.move(key, dbIndex));
    }

    /**
     * 序列化key
     *
     * @param key Key
     * @return Key字节数组
     */
    public byte[] dump(String key) {
        return redisTemplate.dump(key);
    }

    /* -------------------------------------String相关操作----------------------------------- */

    /**
     * 设置Key/Value,如果存在覆盖
     *
     * @param key   Key
     * @param value 值
     */
    public void set(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 设置Key/Value,并指定过期时间
     *
     * @param key     Key
     * @param value   值
     * @param timeout 过期时间
     * @param unit    过期时间单位
     */
    public void setExpire(String key, String value, long timeout, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, value, timeout, unit);
    }

    /**
     * 设置Key/Value,如果存在Key返回False,常用于接口幂等性判定
     *
     * @param key   Key
     * @param value 值
     */
    public boolean setIfAbsent(String key, String value) {
        return Boolean.TRUE.equals(redisTemplate.opsForValue().setIfAbsent(key, value));
    }

    /**
     * 批量设置
     *
     * @param maps key-value键值对Map
     */
    public void multiSet(Map<String, String> maps) {
        redisTemplate.opsForValue().multiSet(maps);
    }

    /**
     * 批量设置,如果存在Key返回False,常用于接口幂等性判定
     *
     * @param maps key-value键值对Map
     * @return 是否添加成功
     */
    public boolean multiSetIfAbsent(Map<String, String> maps) {
        return Boolean.TRUE.equals(redisTemplate.opsForValue().multiSetIfAbsent(maps));
    }

    /**
     * 获取指定key的值
     *
     * @param key Key
     * @return 值
     */
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 批量获取
     *
     * @param keys Key集合
     * @return 值集合
     */
    public List<Object> multiGet(Collection<String> keys) {
        return redisTemplate.opsForValue().multiGet(keys);
    }

    /**
     * 将给定key的值设为value,并返回key的旧值
     *
     * @param key   Key
     * @param value 新值
     * @return 旧值
     */
    public Object getAndSet(String key, String value) {
        return redisTemplate.opsForValue().getAndSet(key, value);
    }

    /**
     * 设置Key/Value,Value从Offset开始覆盖旧的Value值
     *
     * @param key    Key
     * @param value  值
     * @param offset 偏移量
     */
    public void setRange(String key, String value, long offset) {
        redisTemplate.opsForValue().set(key, value, offset);
    }

    /**
     * 截取值并返回
     *
     * @param key   Key
     * @param start 开始位置
     * @param end   结束位置
     * @return 截取后的Value字符串
     */
    public String getRange(String key, long start, long end) {
        return redisTemplate.opsForValue().get(key, start, end);
    }

    /**
     * 获取值的长度
     *
     * @param key Key
     * @return Value的长度
     */
    public Long size(String key) {
        return redisTemplate.opsForValue().size(key);
    }

    /**
     * 值+1
     *
     * @param key Key
     * @return 自增后的值
     */
    public Long incr(String key) {
        return redisTemplate.opsForValue().increment(key);
    }

    /**
     * 值+指定量
     *
     * @param key       Key
     * @param increment 自增量
     * @return 自增后的值
     */
    public Long incrBy(String key, long increment) {
        return redisTemplate.opsForValue().increment(key, increment);
    }

    /**
     * 值+指定量
     *
     * @param key       Key
     * @param increment 自增量
     * @return 自增后的值
     */
    public Double incrByFloat(String key, double increment) {
        return redisTemplate.opsForValue().increment(key, increment);
    }

    /**
     * 值-1
     *
     * @param key Key
     * @return 自减后的值
     */
    public Long decr(String key) {
        return redisTemplate.opsForValue().decrement(key);
    }

    /**
     * 值-指定量
     *
     * @param key       Key
     * @param increment 自减量
     * @return 自减后的值
     */
    public Long decrBy(String key, long increment) {
        return redisTemplate.opsForValue().decrement(key, increment);
    }

    /**
     * 在值末尾追加值
     *
     * @param key   Key
     * @param value 值
     * @return 追加后的值
     */
    public Integer append(String key, String value) {
        return redisTemplate.opsForValue().append(key, value);
    }

    /**
     * 设置位图
     *
     * @param key    Key
     * @param offset 偏移量
     * @param value  值 TRUE(1)/FALSE(0)
     * @return 是否设置成功
     */
    public boolean setBit(String key, long offset, boolean value) {
        return Boolean.TRUE.equals(redisTemplate.opsForValue().setBit(key, offset, value));
    }

    /**
     * 获取位图
     *
     * @param key    Key
     * @param offset 偏移量
     * @return 值 TRUE(1)/FALSE(0)
     */
    public boolean getBit(String key, long offset) {
        return Boolean.TRUE.equals(redisTemplate.opsForValue().getBit(key, offset));
    }

    /* -------------------------------------Hash相关操作----------------------------------- */

    /**
     * 存入Hash键值对,存在即覆盖
     *
     * @param key   Key
     * @param field HashKey
     * @param value 值
     */
    public void hPut(String key, String field, String value) {
        redisTemplate.opsForHash().put(key, field, value);
    }

    /**
     * 批量存入Hash键值对
     *
     * @param key  Key
     * @param maps HashKey-值Map
     */
    public void hPutAll(String key, Map<String, String> maps) {
        redisTemplate.opsForHash().putAll(key, maps);
    }

    /**
     * 存入Hash键值对,存在即报错
     *
     * @param key   Key
     * @param field HashKey
     * @param value 值
     * @return 是否设置成功
     */
    public boolean hPutIfAbsent(String key, String field, String value) {
        return redisTemplate.opsForHash().putIfAbsent(key, field, value);
    }

    /**
     * 获取Hash值
     *
     * @param key   Key
     * @param field HashKey
     * @return 值
     */
    public Object hGet(String key, String field) {
        return redisTemplate.opsForHash().get(key, field);
    }

    /**
     * 获取Key包含的所有HashKey
     *
     * @param key Key
     * @return Key包含的所有HashKey
     */
    public Map<Object, Object> hGetAll(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * 批量获取Hash值
     *
     * @param key    Key
     * @param fields HashKey集合
     * @return 值集合
     */
    public List<Object> hMultiGet(String key, Collection<Object> fields) {
        return redisTemplate.opsForHash().multiGet(key, fields);
    }

    /**
     * 删除一个或多个哈希表字段
     *
     * @param key    Key
     * @param fields HashKey集合
     * @return 删除成功数量
     */
    public Long hDelete(String key, Object... fields) {
        return redisTemplate.opsForHash().delete(key, fields);
    }

    /**
     * 获取Key对应的HashMap中,HashKey是否存在
     *
     * @param key   Key
     * @param field HashKey
     * @return Key对应的HashMap中, HashKey是否存在
     */
    public boolean hExists(String key, String field) {
        return redisTemplate.opsForHash().hasKey(key, field);
    }

    /**
     * 自增
     *
     * @param key       Key
     * @param field     HashKey
     * @param increment 自增值
     * @return 自增后的值
     */
    public Long hIncrBy(String key, Object field, long increment) {
        return redisTemplate.opsForHash().increment(key, field, increment);
    }

    /**
     * 自增
     *
     * @param key   Key
     * @param field HashKey
     * @param delta 自增值
     * @return 自增后的值
     */
    public Double hIncrByFloat(String key, Object field, double delta) {
        return redisTemplate.opsForHash().increment(key, field, delta);
    }

    /**
     * 获取Key对应HashMap包含HashKey的数量
     *
     * @param key Key
     * @return Key对应HashMap包含HashKey的数量
     */
    public Long hSize(String key) {
        return redisTemplate.opsForHash().size(key);
    }

    /**
     * 获取Key对应HashMap包含HashKey
     *
     * @param key Key
     * @return Key对应HashMap包含HashKey集合
     */
    public Set<Object> hKeys(String key) {
        return redisTemplate.opsForHash().keys(key);
    }

    /**
     * 获取Key对应HashMap包含值
     *
     * @param key Key
     * @return Key对应HashMap包含值
     */
    public List<Object> hValues(String key) {
        return redisTemplate.opsForHash().values(key);
    }

    /**
     * 获取Key对应HashMap
     *
     * @param key     Key
     * @param options 可选项
     * @return Key对应HashMap
     */
    public Cursor<Map.Entry<Object, Object>> hScan(String key, ScanOptions options) {
        return redisTemplate.opsForHash().scan(key, options);
    }

    /* -------------------------------------List相关操作----------------------------------- */

    /**
     * 获取队列中指定位置的值
     *
     * @param key   Key
     * @param index 指定位置
     * @return 队列中指定位置的值
     */
    public Object lIndex(String key, long index) {
        return redisTemplate.opsForList().index(key, index);
    }

    /**
     * 获取队列中指定范围的值
     *
     * @param key   Key
     * @param start 开始位置, 0是开始位置
     * @param end   结束位置, -1返回所有
     * @return 队列中指定范围的值
     */
    public List<Object> lRange(String key, long start, long end) {
        return redisTemplate.opsForList().range(key, start, end);
    }

    /**
     * 向队列左侧Push值
     *
     * @param key   Key
     * @param value 值
     * @return 队列中值的总个数
     */
    public Long lLeftPush(String key, String value) {
        return redisTemplate.opsForList().leftPush(key, value);
    }

    /**
     * 向队列左侧批量Push值
     *
     * @param key   Key
     * @param value 值
     * @return 队列中值的总个数
     */
    public Long lLeftPushAll(String key, String... value) {
        return redisTemplate.opsForList().leftPushAll(key, value);
    }

    /**
     * 向队列左侧批量Push值
     *
     * @param key   Key
     * @param value 值集合
     * @return 队列中值的总个数
     */
    public Long lLeftPushAll(String key, Collection<String> value) {
        return redisTemplate.opsForList().leftPushAll(key, value);
    }

    /**
     * 向队列左侧Push值,如果存在报错
     *
     * @param key   Key
     * @param value 值
     * @return 队列中值的总个数
     */
    public Long lLeftPushIfPresent(String key, String value) {
        return redisTemplate.opsForList().leftPushIfPresent(key, value);
    }

    /**
     * 向队列左侧Push值,如果pivot存在,在pivot前面添加
     *
     * @param key   Key
     * @param pivot 当前值
     * @param value 值
     * @return 队列中值的总个数
     */
    public Long lLeftPush(String key, String pivot, String value) {
        return redisTemplate.opsForList().leftPush(key, pivot, value);
    }

    /**
     * 向队列右侧Push值
     *
     * @param key   Key
     * @param value 值
     * @return 队列中值的总个数
     */
    public Long lRightPush(String key, String value) {
        return redisTemplate.opsForList().rightPush(key, value);
    }

    /**
     * 向队列右侧批量Push值
     *
     * @param key   Key
     * @param value 值
     * @return 队列中值的总个数
     */
    public Long lRightPushAll(String key, String... value) {
        return redisTemplate.opsForList().rightPushAll(key, value);
    }

    /**
     * 向队列右侧批量Push值
     *
     * @param key   Key
     * @param value 值
     * @return 队列中值的总个数
     */
    public Long lRightPushAll(String key, Collection<String> value) {
        return redisTemplate.opsForList().rightPushAll(key, value);
    }

    /**
     * 向队列右侧Push值,如果存在报错
     *
     * @param key   Key
     * @param value 值
     * @return 队列中值的总个数
     */
    public Long lRightPushIfPresent(String key, String value) {
        return redisTemplate.opsForList().rightPushIfPresent(key, value);
    }

    /**
     * 向队列右侧Push值,如果pivot存在,在pivot前面添加
     *
     * @param key   Key
     * @param pivot 当前值
     * @param value 值
     * @return 队列中值的总个数
     */
    public Long lRightPush(String key, String pivot, String value) {
        return redisTemplate.opsForList().rightPush(key, pivot, value);
    }

    /**
     * 设置队列中指定位置的值
     *
     * @param key   Key
     * @param index 位置
     * @param value 值
     */
    public void lSet(String key, long index, String value) {
        redisTemplate.opsForList().set(key, index, value);
    }

    /**
     * 队列左侧弹出值
     *
     * @param key Key
     * @return 弹出值
     */
    public Object lLeftPop(String key) {
        return redisTemplate.opsForList().leftPop(key);
    }

    /**
     * 队列左侧弹出值,如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止
     *
     * @param key     Key
     * @param timeout 阻塞时间
     * @param unit    阻塞时间单位
     * @return 弹出值
     */
    public Object lBlockLeftPop(String key, long timeout, TimeUnit unit) {
        return redisTemplate.opsForList().leftPop(key, timeout, unit);
    }

    /**
     * 队列右侧弹出值
     *
     * @param key Key
     * @return 弹出值
     */
    public Object lRightPop(String key) {
        return redisTemplate.opsForList().rightPop(key);
    }

    /**
     * 队列右侧弹出值,如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止
     *
     * @param key     Key
     * @param timeout 阻塞时间
     * @param unit    阻塞时间单位
     * @return 弹出值
     */
    public Object lBlockRightPop(String key, long timeout, TimeUnit unit) {
        return redisTemplate.opsForList().rightPop(key, timeout, unit);
    }

    /**
     * 队列右侧弹出值并向目标队列左侧Push
     *
     * @param sourceKey 源队列Key
     * @param targetKey 目标队列Key
     * @return 弹出值
     */
    public Object lRightPopAndLeftPush(String sourceKey, String targetKey) {
        return redisTemplate.opsForList().rightPopAndLeftPush(sourceKey, targetKey);
    }

    /**
     * 队列右侧弹出值并向目标队列左侧Push,如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止
     *
     * @param sourceKey 源队列Key
     * @param targetKey 目标队列Key
     * @param timeout   阻塞时间
     * @param unit      阻塞时间单位
     * @return 弹出值
     */
    public Object lBlockRightPopAndLeftPush(String sourceKey, String targetKey, long timeout, TimeUnit unit) {
        return redisTemplate.opsForList().rightPopAndLeftPush(sourceKey, targetKey, timeout, unit);
    }

    /**
     * 删除队列中值等于value得元素
     *
     * @param key   Key
     * @param index index=0, 删除所有值等于value的元素;
     *              index>0, 从头部开始删除第一个值等于value的元素;
     *              index<0, 从尾部开始删除第一个值等于value的元素;
     * @param value 值
     * @return 删除后队列长度
     */
    public Long lRemove(String key, long index, String value) {
        return redisTemplate.opsForList().remove(key, index, value);
    }

    /**
     * 裁剪队列
     *
     * @param key   Key
     * @param start 起始位置
     * @param end   结束位置
     */
    public void lTrim(String key, long start, long end) {
        redisTemplate.opsForList().trim(key, start, end);
    }

    /**
     * 获取队列长度
     *
     * @param key Key
     * @return 队列长度
     */
    public Long lLen(String key) {
        return redisTemplate.opsForList().size(key);
    }

    /* -------------------------------------Set相关操作----------------------------------- */

    /**
     * Set添加元素
     *
     * @param key    Key
     * @param values 值
     * @return Set长度
     */
    public Long sAdd(String key, String... values) {
        return redisTemplate.opsForSet().add(key, values);
    }

    /**
     * Set移除元素
     *
     * @param key    Key
     * @param values 值
     * @return Set长度
     */
    public Long sRemove(String key, Object... values) {
        return redisTemplate.opsForSet().remove(key, values);
    }

    /**
     * 移除并返回集合的一个随机元素
     *
     * @param key Key
     * @return 集合元素
     */
    public Object sPop(String key) {
        return redisTemplate.opsForSet().pop(key);
    }

    /**
     * 将元素value从一个集合移到另一个集合
     *
     * @param sourceKey 源 Key
     * @param value     值
     * @param targetKey 目标Key
     * @return 是否移动成功
     */
    public boolean sMove(String sourceKey, String value, String targetKey) {
        return Boolean.TRUE.equals(redisTemplate.opsForSet().move(sourceKey, value, targetKey));
    }

    /**
     * 获取集合的大小
     *
     * @param key Key
     * @return 集合大小
     */
    public Long sSize(String key) {
        return redisTemplate.opsForSet().size(key);
    }

    /**
     * 判断集合是否包含value
     *
     * @param key   Key
     * @param value 值
     * @return 是否包含
     */
    public boolean sIsMember(String key, Object value) {
        return Boolean.TRUE.equals(redisTemplate.opsForSet().isMember(key, value));
    }

    /**
     * 获取两个集合的交集
     *
     * @param key      Key
     * @param otherKey 另一个集合Key
     * @return 两个集合的交集
     */
    public Set<Object> sIntersect(String key, String otherKey) {
        return redisTemplate.opsForSet().intersect(key, otherKey);
    }

    /**
     * 获取key集合与多个集合的交集
     *
     * @param key       Key
     * @param otherKeys 另外集合Key
     * @return key集合与多个集合的交集
     */
    public Set<Object> sIntersect(String key, Collection<String> otherKeys) {
        return redisTemplate.opsForSet().intersect(key, otherKeys);
    }

    /**
     * key集合与otherKey集合的交集存储到targetKey集合中
     *
     * @param key       Key
     * @param otherKey  另一个集合Key
     * @param targetKey 目标集合Key
     * @return targetKey集合长度
     */
    public Long sIntersectAndStore(String key, String otherKey, String targetKey) {
        return redisTemplate.opsForSet().intersectAndStore(key, otherKey, targetKey);
    }

    /**
     * key集合与多个集合的交集存储到targetKey集合中
     *
     * @param key       Key
     * @param otherKeys 另外集合Key
     * @param targetKey 目标集合Key
     * @return targetKey集合长度
     */
    public Long sIntersectAndStore(String key, Collection<String> otherKeys, String targetKey) {
        return redisTemplate.opsForSet().intersectAndStore(key, otherKeys, targetKey);
    }

    /**
     * 获取两个集合的并集
     *
     * @param key      Key
     * @param otherKey 另一个集合Key
     * @return 两个集合的并集
     */
    public Set<Object> sUnion(String key, String otherKey) {
        return redisTemplate.opsForSet().union(key, otherKey);
    }

    /**
     * 获取key集合与多个集合的并集
     *
     * @param key       Key
     * @param otherKeys 另外集合Key
     * @return key集合与多个集合的并集
     */
    public Set<Object> sUnion(String key, Collection<String> otherKeys) {
        return redisTemplate.opsForSet().union(key, otherKeys);
    }

    /**
     * key集合与otherKey集合的并集存储到targetKey中
     *
     * @param key       Key
     * @param otherKey  另一个集合Key
     * @param targetKey 目标Key
     * @return targetKey集合长度
     */
    public Long sUnionAndStore(String key, String otherKey, String targetKey) {
        return redisTemplate.opsForSet().unionAndStore(key, otherKey, targetKey);
    }

    /**
     * key集合与多个集合的并集存储到targetKey中
     *
     * @param key       Key
     * @param otherKeys 另外集合Key
     * @param targetKey 目标Key
     * @return targetKey集合长度
     */
    public Long sUnionAndStore(String key, Collection<String> otherKeys, String targetKey) {
        return redisTemplate.opsForSet().unionAndStore(key, otherKeys, targetKey);
    }

    /**
     * 获取两个集合的差集
     *
     * @param key      Key
     * @param otherKey 另一个集合Key
     * @return 两个集合的差集
     */
    public Set<Object> sDifference(String key, String otherKey) {
        return redisTemplate.opsForSet().difference(key, otherKey);
    }

    /**
     * 获取key集合与多个集合的差集
     *
     * @param key       Key
     * @param otherKeys 另外集合Key
     * @return key集合与多个集合的差集
     */
    public Set<Object> sDifference(String key, Collection<String> otherKeys) {
        return redisTemplate.opsForSet().difference(key, otherKeys);
    }

    /**
     * key集合与otherKey集合的差集存储到targetKey中
     *
     * @param key       Key
     * @param otherKey  另一个集合Key
     * @param targetKey 目标Key
     * @return targetKey集合长度
     */
    public Long sDifference(String key, String otherKey, String targetKey) {
        return redisTemplate.opsForSet().differenceAndStore(key, otherKey, targetKey);
    }

    /**
     * key集合与多个集合的差集存储到targetKey中
     *
     * @param key       Key
     * @param otherKeys 另外集合Key
     * @param targetKey 目标Key
     * @return targetKey集合长度
     */
    public Long sDifference(String key, Collection<String> otherKeys, String targetKey) {
        return redisTemplate.opsForSet().differenceAndStore(key, otherKeys, targetKey);
    }

    /**
     * 获取集合所有元素
     *
     * @param key Key
     * @return 集合所有元素
     */
    public Set<Object> setMembers(String key) {
        return redisTemplate.opsForSet().members(key);
    }

    /**
     * 随机获取集合中的一个元素
     *
     * @param key Key
     * @return 集合中的随机元素
     */
    public Object sRandomMember(String key) {
        return redisTemplate.opsForSet().randomMember(key);
    }

    /**
     * 随机获取集合中count个元素
     *
     * @param key   Key
     * @param count 个数
     * @return count个随机元素
     */
    public List<Object> sRandomMembers(String key, long count) {
        return redisTemplate.opsForSet().randomMembers(key, count);
    }

    /**
     * 随机获取集合中count个元素并且去除重复的
     *
     * @param key   Key
     * @param count 个数
     * @return 去重后的count个随机元素
     */
    public Set<Object> sDistinctRandomMembers(String key, long count) {
        return redisTemplate.opsForSet().distinctRandomMembers(key, count);
    }

    /* -------------------------------------ZSet相关操作----------------------------------- */

    /**
     * 添加元素,有序集合是按照元素的score值由小到大排列
     *
     * @param key   Key
     * @param value 值
     * @param score 分数
     * @return 是否添加成功
     */
    public boolean zAdd(String key, String value, double score) {
        return Boolean.TRUE.equals(redisTemplate.opsForZSet().add(key, value, score));
    }

    /**
     * 批量添加元素,有序集合是按照元素的score值由小到大排列
     *
     * @param key    Key
     * @param values 值-分数集合
     * @return 添加成功个数
     */
    public Long zAdd(String key, Set<ZSetOperations.TypedTuple<Object>> values) {
        return redisTemplate.opsForZSet().add(key, values);
    }

    /**
     * 增加元素的score值，并返回增加后的值
     *
     * @param key   Key
     * @param value 值
     * @param delta 增加多少分数
     * @return 增加后的分数值
     */
    public Double zIncrementScore(String key, String value, double delta) {
        return redisTemplate.opsForZSet().incrementScore(key, value, delta);
    }

    /**
     * 返回元素在集合的排名,按元素的score值由小到大排列
     *
     * @param key   Key
     * @param value 值
     * @return 排名, 0表示第一位
     */
    public Long zRank(String key, Object value) {
        return redisTemplate.opsForZSet().rank(key, value);
    }

    /**
     * 返回元素在集合的排名,按元素的score值由大到小排列
     *
     * @param key   Key
     * @param value 值
     * @return 排名, 0表示第一位
     */
    public Long zReverseRank(String key, Object value) {
        return redisTemplate.opsForZSet().reverseRank(key, value);
    }

    /**
     * 正序获取集合的元素
     *
     * @param key   Key
     * @param start 开始位置
     * @param end   结束位置, -1查询所有
     * @return 集合的元素
     */
    public Set<Object> zRange(String key, long start, long end) {
        return redisTemplate.opsForZSet().range(key, start, end);
    }

    /**
     * 倒序获取集合的元素
     *
     * @param key   Key
     * @param start 开始位置
     * @param end   结束位置, -1查询所有
     * @return 集合的元素
     */
    public Set<Object> zReverseRange(String key, long start, long end) {
        return redisTemplate.opsForZSet().reverseRange(key, start, end);
    }

    /**
     * 正序获取集合的元素, 并返回score值
     *
     * @param key   Key
     * @param start 开始位置
     * @param end   结束位置, -1查询所有
     * @return 集合的元素
     */
    public Set<ZSetOperations.TypedTuple<Object>> zRangeWithScores(String key, long start, long end) {
        return redisTemplate.opsForZSet().rangeWithScores(key, start, end);
    }

    /**
     * 倒序获取集合的元素, 并返回score值
     *
     * @param key   Key
     * @param start 开始位置
     * @param end   结束位置, -1查询所有
     * @return 集合的元素
     */
    public Set<ZSetOperations.TypedTuple<Object>> zReverseRangeWithScores(String key, long start, long end) {
        return redisTemplate.opsForZSet().reverseRangeWithScores(key, start, end);
    }

    /**
     * 获取集合的元素,根据Score值从小到大排序
     *
     * @param key Key
     * @param min Score最小值
     * @param max Score最大值
     * @return 集合元素
     */
    public Set<Object> zRangeByScore(String key, double min, double max) {
        return redisTemplate.opsForZSet().rangeByScore(key, min, max);
    }

    /**
     * 获取集合的元素,根据Score值从大到小排序
     *
     * @param key Key
     * @param min Score最小值
     * @param max Score最大值
     * @return 集合元素
     */
    public Set<Object> zReverseRangeByScore(String key, double min, double max) {
        return redisTemplate.opsForZSet().reverseRangeByScore(key, min, max);
    }

    /**
     * 获取集合的元素,根据Score值从小到大排序,并返回Score值
     *
     * @param key Key
     * @param min Score最小值
     * @param max Score最大值
     * @return 集合元素
     */
    public Set<ZSetOperations.TypedTuple<Object>> zRangeByScoreWithScores(String key, double min, double max) {
        return redisTemplate.opsForZSet().rangeByScoreWithScores(key, min, max);
    }

    /**
     * 获取集合的元素,指定位置,根据Score值从小到大排序,并返回Score值
     *
     * @param key   Key
     * @param min   Score最小值
     * @param max   Score最大值
     * @param start 起始位置
     * @param end   结束位置
     * @return 集合元素
     */
    public Set<ZSetOperations.TypedTuple<Object>> zRangeByScoreWithScores(String key, double min, double max, long start, long end) {
        return redisTemplate.opsForZSet().rangeByScoreWithScores(key, min, max, start, end);
    }

    /**
     * 获取集合的元素,根据Score值从大到小排序,并返回Score值
     *
     * @param key Key
     * @param min Score最小值
     * @param max Score最大值
     * @return 集合元素
     */
    public Set<ZSetOperations.TypedTuple<Object>> zReverseRangeByScoreWithScores(String key, double min, double max) {
        return redisTemplate.opsForZSet().reverseRangeByScoreWithScores(key, min, max);
    }

    /**
     * 获取集合的元素,指定位置,根据Score值从大到小排序,并返回Score值
     *
     * @param key   Key
     * @param min   Score最小值
     * @param max   Score最大值
     * @param start 起始位置
     * @param end   结束位置
     * @return 集合元素
     */
    public Set<Object> zReverseRangeByScore(String key, double min, double max, long start, long end) {
        return redisTemplate.opsForZSet().reverseRangeByScore(key, min, max, start, end);
    }

    /**
     * 根据score值获取集合元素数量
     *
     * @param key Key
     * @param min Score最小值
     * @param max Score最大值
     * @return 根据score值获取的集合元素数量
     */
    public Long zCount(String key, double min, double max) {
        return redisTemplate.opsForZSet().count(key, min, max);
    }

    /**
     * 获取集合大小
     *
     * @param key Key
     * @return 集合大小
     */
    public Long zSize(String key) {
        return redisTemplate.opsForZSet().size(key);
    }

    /**
     * 获取集合大小
     *
     * @param key Key
     * @return 集合大小
     */
    public Long zCard(String key) {
        return redisTemplate.opsForZSet().zCard(key);
    }

    /**
     * 获取集合中value元素的score值
     *
     * @param key   Key
     * @param value 值
     * @return 分数
     */
    public Double zScore(String key, Object value) {
        return redisTemplate.opsForZSet().score(key, value);
    }

    /**
     * 批量移除元素
     *
     * @param key    Key
     * @param values 值
     * @return 成功移除个数
     */
    public Long zRemove(String key, Object... values) {
        return redisTemplate.opsForZSet().remove(key, values);
    }

    /**
     * 移除指定索引位置的成员
     *
     * @param key   Key
     * @param start 起始位置
     * @param end   结束位置
     * @return 移除成功的元素个数
     */
    public Long zRemoveRange(String key, long start, long end) {
        return redisTemplate.opsForZSet().removeRange(key, start, end);
    }

    /**
     * 根据指定的score值的范围来移除成员
     *
     * @param key Key
     * @param min Score最小值
     * @param max Score最大值
     * @return 移除成功的元素个数
     */
    public Long zRemoveRangeByScore(String key, double min, double max) {
        return redisTemplate.opsForZSet().removeRangeByScore(key, min, max);
    }

    /**
     * 获取key和另一个集合的并集并存储在targetKey中
     *
     * @param key       Key
     * @param otherKey  另一个集合Key
     * @param targetKey 目标Key
     * @return 并集Key个数
     */
    public Long zUnionAndStore(String key, String otherKey, String targetKey) {
        return redisTemplate.opsForZSet().unionAndStore(key, otherKey, targetKey);
    }

    /**
     * 获取key和另外多个集合的并集并存储在targetKey中
     *
     * @param key       Key
     * @param otherKeys 另外集合Key
     * @param targetKey 目标Key
     * @return 并集Key个数
     */
    public Long zUnionAndStore(String key, Collection<String> otherKeys, String targetKey) {
        return redisTemplate.opsForZSet().unionAndStore(key, otherKeys, targetKey);
    }

    /**
     * 获取key和另一个集合的交集并存储在targetKey中
     *
     * @param key       Key
     * @param otherKey  另一个集合Key
     * @param targetKey 目标Key
     * @return 交集Key个数
     */
    public Long zIntersectAndStore(String key, String otherKey, String targetKey) {
        return redisTemplate.opsForZSet().intersectAndStore(key, otherKey, targetKey);
    }

    /**
     * 获取key和另一个集合的交集并存储在targetKey中
     *
     * @param key       Key
     * @param otherKeys 另外集合Key
     * @param targetKey 目标Key
     * @return 交集Key个数
     */
    public Long zIntersectAndStore(String key, Collection<String> otherKeys, String targetKey) {
        return redisTemplate.opsForZSet().intersectAndStore(key, otherKeys, targetKey);
    }
}