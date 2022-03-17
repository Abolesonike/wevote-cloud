package com.fizzy.commentservice.feign;

import com.fizzy.core.entity.Message;
import com.fizzy.core.entity.SysUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author FizzyElf
 * @version 1.0
 * @date 2022/3/7 16:29
 */
@FeignClient(name = "post-service")
public interface PostServiceFeign {

    /**
     * 插入消息
     * @param message 消息
     * @return 是否成功
     */
    @PostMapping("/message/insert")
    Boolean insertMessage(@RequestBody Message message);
}