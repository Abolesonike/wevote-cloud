package com.fizzy.gateway.config;

import com.alibaba.fastjson.JSONObject;
import com.fizzy.core.utils.Result;
import com.fizzy.gateway.feign.AuthFeign;
import com.fizzy.gateway.uilts.SpringUtils;
import com.fizzy.redis.utils.RedisUtil;
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

import javax.servlet.http.HttpSession;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.*;

/**
 * Author FizzyElf
 * Date 2021/10/18 16:57
 * GateWay过滤器
 * 拦截所有GateWay的请求，进行鉴权
 */
@Component
public class ShiroFilter implements GlobalFilter, Ordered {

    AuthFeign authFeign;

    @Autowired
    RedisUtil redisUtil;


    ExecutorService executorService = Executors.newFixedThreadPool(1);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        System.out.println("进入GateWay Shiro过滤器");
        // 请求对象
        ServerHttpRequest request = exchange.getRequest();
        // Cookie放入redis，设置过期时间一天，方便feign拦截器取得
        redisUtil.setExpire("Cookie",request.getHeaders().getFirst("Cookie"), 1, TimeUnit.DAYS);
        // 响应对象
        ServerHttpResponse response = exchange.getResponse();
        String token = request.getHeaders().getFirst("token");
        String userCode = request.getHeaders().getFirst("userCode");
        System.out.println(token+userCode);
        String requestUrl = exchange.getRequest().getURI().getRawPath();
        System.out.println("requestUrl:"+requestUrl);

        // 1.检查是否是 不需要登录 或者 不需要权限 的接口
        if("/login".equals(requestUrl)){
            return chain.filter(exchange);
        }

        // 2.检查是否有token
        if(token == null || "".equals(token)){
            return endResponse(response, new Result(402,"未登录"));
        }

        // 3.OpenFeign，查看接口权限
        authFeign = SpringUtils.getBean(AuthFeign.class);

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
