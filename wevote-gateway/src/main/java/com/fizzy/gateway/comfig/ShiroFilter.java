package com.fizzy.gateway.comfig;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * Author FizzyElf
 * Date 2021/10/18 16:57
 */
@Component
public class ShiroFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        System.out.println("进入GateWay Shiro过滤器");
        // 请求对象
        ServerHttpRequest request = exchange.getRequest();
        // 响应对象
        ServerHttpResponse response = exchange.getResponse();
        String token = request.getHeaders().getFirst("token");
        String userCode = request.getHeaders().getFirst("userCode");
        System.out.println(token+userCode);
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
