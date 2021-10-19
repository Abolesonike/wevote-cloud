package com.fizzy.gateway.fegin;

import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * Author FizzyElf
 * Date 2021/10/19 16:11
 */
@Component
public class GatewayFeignFallbackFactory implements FallbackFactory<AuthFeign> {
    @Override
    public AuthFeign create(Throwable throwable) {
        return (name, token) -> {
            System.out.println("授权服务超时...");
            return false;
        };
    }
}
