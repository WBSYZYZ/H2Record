package com.gateway.filter;

import com.alibaba.nacos.common.utils.MapUtil;
import com.gateway.config.properties.IgnoreWhiteProperties;
import com.record.basic.core.constant.CacheConstants;
import com.record.basic.core.constant.Constants;
import com.record.basic.core.constant.HttpStatus;
import com.record.basic.core.constant.SecurityConstants;
import com.record.basic.core.utils.SecurityUtils;
import com.record.basic.core.utils.ServletUtils;
import com.record.basic.core.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 网关鉴权
 * 
 * @author lw
 */
@Component
public class AuthFilter implements GlobalFilter, Ordered
{
    private static final Logger log = LoggerFactory.getLogger(AuthFilter.class);

    private final static long EXPIRE_TIME = Constants.TOKEN_EXPIRE * 60;

    // 排除过滤的 uri 地址，nacos自行添加
    @Autowired
    private IgnoreWhiteProperties ignoreWhite;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain)
    {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpRequest.Builder mutate = request.mutate();

        String url = request.getURI().getPath();
        // 跳过不需要验证的路径
        if (StringUtils.matches(url, ignoreWhite.getWhites()))
        {
            return chain.filter(exchange);
        }
        String token = getToken(request);
        if (StringUtils.isEmpty(token))
        {
            return unauthorizedResponse(exchange, "令牌不能为空");
        }
        Map userMap =stringRedisTemplate.opsForHash().entries(getTokenKey(token));
        if (MapUtil.isEmpty(userMap))
        {
            return unauthorizedResponse(exchange, "登录状态已过期");
        }
        Object userid = userMap.get("userId");
        Object nickName = userMap.get("nickName");
        if (userid==null || nickName==null)
        {
            return unauthorizedResponse(exchange, "令牌验证失败");
        }

        // 设置过期时间
        stringRedisTemplate.expire(getTokenKey(token), EXPIRE_TIME, TimeUnit.MINUTES);
        // 设置用户信息到请求
        addHeader(mutate, SecurityConstants.DETAILS_USER_ID, userid);
        addHeader(mutate, SecurityConstants.DETAILS_USERNAME, nickName);
        // 内部请求来源参数清除
        removeHeader(mutate, SecurityConstants.FROM_SOURCE);
        return chain.filter(exchange.mutate().request(mutate.build()).build());
    }

    private void addHeader(ServerHttpRequest.Builder mutate, String name, Object value)
    {
        if (value == null)
        {
            return;
        }
        String valueStr = value.toString();
        String valueEncode = ServletUtils.urlEncode(valueStr);
        mutate.header(name, valueEncode);
    }

    private void removeHeader(ServerHttpRequest.Builder mutate, String name)
    {
        mutate.headers(httpHeaders -> httpHeaders.remove(name)).build();
    }

    private Mono<Void> unauthorizedResponse(ServerWebExchange exchange, String msg)
    {
        log.error("[鉴权异常处理]请求路径:{}", exchange.getRequest().getPath());
        return ServletUtils.webFluxResponseWriter(exchange.getResponse(), msg, HttpStatus.UNAUTHORIZED);
    }

    /**
     * 获取缓存key
     */
    private String getTokenKey(String token)
    {
        return CacheConstants.LOGIN_TOKEN_KEY + token;
    }

    /**
     * 获取请求token
     */
    private String getToken(ServerHttpRequest request)
    {
        String token = request.getHeaders().getFirst(SecurityConstants.TOKEN_AUTHENTICATION);
        return SecurityUtils.replaceTokenPrefix(token);
    }

    @Override
    public int getOrder()
    {
        return -200;
    }
}