package com.fizzy.gateway.fegin;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="auth-service",fallbackFactory = GatewayFeignFallbackFactory.class)
public interface AuthFeign {
    @GetMapping ( "/isPermitted")
    boolean isPermitted(@RequestParam String requestURI, @RequestParam String token);
}
