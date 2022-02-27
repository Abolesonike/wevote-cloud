package com.fizzy.gateway.config;

import com.fizzy.redis.utils.RedisUtil;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * Author FizzyElf
 * Date 2021/10/26 10:39
 * OpenFeign请求拦截，把Cookie从redis中取出，放入请求头
 * 解决不太服务session不一致的问题
 * @author CDLX
 */
@Configuration
public class FeignRequestInterceptor implements RequestInterceptor {
    @Autowired
    RedisUtil redisUtil;

    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header("Cookie", String.valueOf(redisUtil.get("Cookie")));
    }
}
