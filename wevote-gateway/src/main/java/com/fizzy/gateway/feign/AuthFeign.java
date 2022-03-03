package com.fizzy.gateway.feign;

import com.fizzy.core.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "auth-service")
public interface AuthFeign {
    @RequestMapping(method = RequestMethod.GET, value = "/isPermitted")
    boolean isPermitted(@RequestParam String requestUrl,@RequestParam String token);


    @GetMapping("/goLogin")
    Result goLogin();

    @GetMapping("/auth/test")
    Void authTest();

}
