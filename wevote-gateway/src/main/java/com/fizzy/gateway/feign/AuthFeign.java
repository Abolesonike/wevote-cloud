package com.fizzy.gateway.feign;

import com.fizzy.core.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "auth-service")
public interface AuthFeign {
    @GetMapping("/isPermitted")
    boolean isPermitted(@RequestParam String requestURI,@RequestParam String token);

    @GetMapping("/goLogin")
    Result goLogin();

}
