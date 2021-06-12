package com.lhever.sc.devops.core.support.redis;

import org.springframework.data.redis.core.RedisTemplate;

public abstract class AbstractRedisTemplateUtils<K, V> {

    protected static RedisTemplate template;

    public static void setRedisTemplate(RedisTemplate redisTemplate) {
        if (redisTemplate == null) {
                throw new IllegalStateException("RedisTemplate init error");
        }
        template = redisTemplate;
    }


}
