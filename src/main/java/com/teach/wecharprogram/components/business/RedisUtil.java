package com.teach.wecharprogram.components.business;

import com.google.common.collect.Sets;
import com.teach.wecharprogram.util.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Component
public class RedisUtil {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisDistributedLock lock;


    private byte[] rawKey;

    private BoundListOperations<String, String> listOperations;//noblocking


    public void pushFromHead(String key, String value) {
        bindKeys(key);
        listOperations.leftPush(value);
    }


    public void pushFromTail(String key, String value) {
        bindKeys(key);
        listOperations.rightPush(value);
    }


    public String removeFromHead(String key) {
        bindKeys(key);
        return listOperations.leftPop();
    }


    public String removeFromTail(String key) {
        bindKeys(key);
        return listOperations.rightPop();
    }


    public Long getKeySize(String key) {
        bindKeys(key);
        return listOperations.size();
    }

    private void bindKeys(String key) {
        rawKey = redisTemplate.getKeySerializer().serialize(key);
        listOperations = redisTemplate.boundListOps(key);
    }


    public void saveMap(final String key, Map map) {
        redisTemplate.opsForHash().putAll(key, map);
    }

    public LinkedHashSet<String> getKeys(String keys) {
        Object o = redisTemplate.keys(keys + "*");
        if (Objects.nonNull(o))
            return (LinkedHashSet<String>) o;
        else
            return Sets.newLinkedHashSet();
    }

    public List<Object> mget(Collection<String> keys) {
        List list = stringRedisTemplate.opsForValue().multiGet(keys);
        return list;
    }

    public void saveMap(final String key, long expireTime, Map map) {
        saveMap(key, map);
        redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
    }

    public void set(final String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value);
    }

    public void set(final String key, Object value) {
        stringRedisTemplate.opsForValue().set(key, JSONUtil.toJSONString(value));
    }

    public void set(final String key, Object value, long expireSeconds) {
        stringRedisTemplate.opsForValue().set(key, JSONUtil.toJSONString(value));
        stringRedisTemplate.expire(key, expireSeconds, TimeUnit.SECONDS);
    }

    @Async
    public void setAsync(final String key, String value, Long expireSeconds) {
        stringRedisTemplate.opsForValue().set(key, value);
        if(Objects.nonNull(expireSeconds))
            stringRedisTemplate.expire(key, expireSeconds, TimeUnit.SECONDS);
    }

    public void set(final String key, Object value,long time ,TimeUnit timeUnit) {
        stringRedisTemplate.opsForValue().set(key, JSONUtil.toJSONString(value));
        stringRedisTemplate.expire(key, time, timeUnit);
    }

    public void expire(final String key, long expireTime) {
        redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
    }

    public Long incr(final String key) {
        Long increment = redisTemplate.opsForValue().increment(key);
        return increment;
    }

    public Long decr(final String key) {
        Long decrement = redisTemplate.opsForValue().decrement(key);
        return decrement;
    }

    public void setForObject(final String key, Object value) {
        redisTemplate.boundValueOps(key).set(value);
    }

    public void refreshTime(final String key, long expireTime) {
        redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
    }


    public String get(String key) {
        if (Objects.isNull(stringRedisTemplate.opsForValue().get(key)))
            return null;
        return stringRedisTemplate.opsForValue().get(key);
    }

    public boolean setLock(String key,String value) {
        return lock.setLock(key,value);
    }

    public boolean releaseLock(String key) {
        return lock.releaseLock(key);
    }

    public boolean deleteLike(String key) {
        Set<String> keys = stringRedisTemplate.keys(key + "*");
        stringRedisTemplate.delete(keys);
        return true;
    }


    public Map getMap(String key) {
        return redisTemplate.opsForHash().entries(key);
    }


    public boolean delete(String key) {
        Boolean delete = redisTemplate.delete(key);
        return delete;
    }

    public boolean deleteAll(Set<String> key) {
        Long delete = redisTemplate.delete(key);
        return delete > 0;
    }

}
