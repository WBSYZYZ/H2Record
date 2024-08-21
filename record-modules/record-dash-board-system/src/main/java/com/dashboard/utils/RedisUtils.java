package com.dashboard.utils;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

import static com.dashboard.utils.RedisConstants.LOGIN_USER_KEY;

/**
 * @author Administrator
 */
@Component
public class RedisUtils {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    public Map getRedisUserInfo(HttpServletRequest request){
        String token=request.getHeader("authorization");
        Map map=new HashMap();
        if(token==null){
            return map;
        }
        //基于token获取redis用户
        map=stringRedisTemplate.opsForHash()
                .entries(LOGIN_USER_KEY+token);
        return map;
    }

}
