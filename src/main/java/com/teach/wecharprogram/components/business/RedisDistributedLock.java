package com.teach.wecharprogram.components.business;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;


/**
 * Redis分布式锁工具类
 */
@Component
@Slf4j
public class RedisDistributedLock {

    @Autowired
    private RedisTemplate redisTemplate;

    public static final String UNLOCK_LUA;

    static {
        StringBuilder sb = new StringBuilder();
        sb.append("if redis.call(\"get\",KEYS[1]) == ARGV[1] ");
        sb.append("then ");
        sb.append("    return redis.call(\"del\",KEYS[1]) ");
        sb.append("else ");
        sb.append("    return 0 ");
        sb.append("end ");
        UNLOCK_LUA = sb.toString();
    }

    /**
     * @param key
     * @return 加锁
     */
    public boolean setLock(String key, String value) {
        try {
            Boolean aBoolean = redisTemplate.opsForValue().setIfAbsent(key, value);
            return aBoolean;
        } catch (Exception e) {
            log.error("set redis occured an exception", e);
        }
        return false;
    }

    public boolean get(String key) {
        try {
            Object o = redisTemplate.opsForValue().get(key);
            if (o != null) {
                return Boolean.parseBoolean(o.toString());
            }
        } catch (Exception e) {
            log.error("get redis occured an exception", e);
        }
        return false;
    }

    /**
     * @param key
     * @return 释放锁
     */
    public boolean releaseLock(String key) {
        Boolean delete = redisTemplate.delete(key);
        return delete;
    }

}