package com.dashboard.utils;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.concurrent.TimeUnit;


/**
 * @author lw
 */
@Component
public class RedisLockUtils {

    @Resource
    private RedisTemplate redisTemplate;

    public Boolean lock(String key,String value,Long time){
        Boolean lockStatus=redisTemplate.opsForValue().setIfAbsent(key,value,time,TimeUnit.SECONDS);
        return lockStatus;
    }
    public void unlock(String key,String value){
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        RedisScript<Long> redisScript = new DefaultRedisScript<>(script, Long.class);
        Object result =redisTemplate.execute(redisScript, Collections.singletonList(key), value);
        System.out.println(result);
    }
}
