package com.fizzy.commentservice.feign;

import com.fizzy.core.entity.SysUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author FizzyElf
 * @version 1.0
 * @date 2022/3/7 17:15
 */
@FeignClient(name = "auth-service", path = "/sysUser")
public interface SysUserServiceFeign {
    /**
     * 通过id查询用户
     * @param id id
     * @return 用户
     */
    @GetMapping("/findById")
    SysUser findById(@RequestParam long id);
}
