package com.fizzy.postservice.feign;

import com.fizzy.core.entity.SysUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Author FizzyElf
 * Date 2021/11/3 16:08
 */
@FeignClient(name = "auth-service", path = "/sysUser")
public interface SysUserServiceFeign {
    @GetMapping("/findById")
    public SysUser findById(@RequestParam int id);
}
