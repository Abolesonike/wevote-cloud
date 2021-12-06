package com.fizzy.postservice.feign;

import com.fizzy.core.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service", path = "/user")
public interface UserServiceFeign {
    @GetMapping("/getUserById/{id}")
    User getUserById(@PathVariable("id") int id);
}