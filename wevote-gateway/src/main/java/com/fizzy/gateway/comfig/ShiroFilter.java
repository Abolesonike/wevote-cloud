package com.fizzy.gateway.comfig;

import com.alibaba.fastjson.JSONObject;
import com.fizzy.core.utils.Result;
import com.fizzy.gateway.feign.AuthFeign;
import com.fizzy.gateway.uilts.SpringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.*;

/**
 * Author FizzyElf
 * Date 2021/10/18 16:57
 */
@Component
public class ShiroFilter implements GlobalFilter, Ordered {

    AuthFeign authFeign;

    ExecutorService executorService = Executors.newFixedThreadPool(1);

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
        String requestUrl = exchange.getRequest().getURI().getRawPath();
        System.out.println("requestUrl:"+requestUrl);

        // OpenFeign
        authFeign = SpringUtils.getBean(AuthFeign.class);
//        boolean permitted = authFeign.isPermitted(requestUrl,token);

        // WebFlux异步调用，同步会报错
        boolean permitted = true;
        Future<Boolean> future = executorService.submit(() ->authFeign.isPermitted(requestUrl,token));

        try {
            permitted = future.get();
            System.out.println(permitted);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        if(!permitted){
            return endResponse(response, new Result(401,"未授权"));
        }
        return chain.filter(exchange);
    }

    public Mono<Void> endResponse(ServerHttpResponse response, Result result){
//   String message= JSONObject.toJSONString(code);
        // 响应消息内容对象
        JSONObject message = new JSONObject();
        // 响应状态
        message.put("code", result.getCode());
        // 响应内容
        message.put("message", result.getMessage());
        // 转换响应消息内容对象为字节
        byte[] bits = message.toJSONString().getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = response.bufferFactory().wrap(bits);

        response.getHeaders().add("Content-Type", "text/plain;charset=UTF-8");
        // 返回响应对象
        return response.writeWith(Mono.just(buffer));
    }

    /**
     * 指定过滤器的执行顺序 , 返回值越小,执行优先级越高
     */
    @Override
    public int getOrder() {
        return 10;
    }
}
